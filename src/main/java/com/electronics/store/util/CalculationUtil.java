package com.electronics.store.util;

import com.electronics.store.domainvalue.DiscountType;
import com.electronics.store.model.CartItem;
import com.electronics.store.model.Discount;
import com.electronics.store.model.Product;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class CalculationUtil {
    public static Double calculateItemTotalCost(CartItem cartItem) {
        Double totalCost = (cartItem.getProduct().getProductPrice() * cartItem.getProductQty()) - cartItem.getGrandDiscount();
        return totalCost;
    }

    public static Double calculateItemTotalDiscount(CartItem cartItem) {
        Double discountValue = 0.0;
        Discount discount = cartItem.getDiscountApplied();
        Integer applicableQuantity = cartItem.getProductQty() - discount.getMinimumQty();
        discountValue = ((applicableQuantity * cartItem.getProduct().getProductPrice()) * discount.getDiscountPercent())/100;
        return discountValue;
    }

    public static List<Product> findDiscountedProduct() {
        return null;
    }

    public static Discount getApplicableDiscounts(List<Discount> discounts, Integer quantity) {
        Discount discount = Discount.builder().discountPercent(0).build();
        List<Discount> discountList = discounts.stream()
                .filter(l -> DiscountType.PERCENTAGE.equals(l.getDiscountType())&& (l.getDateExpired().compareTo(ZonedDateTime.now())>0))
                .collect(Collectors.toList());
        if(!discountList.isEmpty()) {
             discount = discountList.get(0);
            for (Discount dc : discountList) {
                if (dc.getMinimumQty() < quantity && dc.getMinimumQty()> discount.getMinimumQty()) {
                    discount = dc;
                }
            }
        }
        return discount;
    }

    public static List<Product> getBundleProduct(List<Discount> discounts, Integer quantity) {
        List<Discount> discountList = discounts.stream()
                .filter(l -> DiscountType.BUNDLE.equals(l.getDiscountType()) && (l.getDateExpired().compareTo(ZonedDateTime.now())>0) && (l.getMinimumQty()<= quantity))
                .collect(Collectors.toList());
        return discountList.stream().map(discount -> discount.getDiscountBundleProduct()).collect(Collectors.toList());

    }

    public static void populateDiscounts(List<Discount> discounts, CartItem cartItem) {
        Integer newQuantity = cartItem.getProductQty();
        cartItem.setDiscountApplied(getApplicableDiscounts(discounts, newQuantity));
        cartItem.setDiscountedProduct(getBundleProduct(discounts, newQuantity));
    }

    public static void populateCosts(CartItem cartItem) {
        cartItem.setGrandDiscount(calculateItemTotalDiscount(cartItem));
        cartItem.setGrandCost(calculateItemTotalCost(cartItem));
    }
}
