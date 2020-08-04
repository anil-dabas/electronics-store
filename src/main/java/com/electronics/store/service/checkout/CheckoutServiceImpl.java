package com.electronics.store.service.checkout;

import com.electronics.store.exception.InsufficientQuantityException;
import com.electronics.store.exception.NoSuchCartExist;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.*;
import com.electronics.store.request.InventoryItemRequest;
import com.electronics.store.service.cart.CartService;
import com.electronics.store.service.discount.DiscountService;
import com.electronics.store.service.inventory.InventoryService;
import com.electronics.store.util.CalculationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private CartService cartService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private DiscountService discountService;
    @Override
    public Cart performCheckout(String cartId) throws NoSuchCartExist, NoSuchProductInStore, InsufficientQuantityException, ProductNotFoundException {
        Cart cart = cartService.getCartByCartId(cartId);
        Map<String,CartItem> cartItemMap = validateCartItems(cart.getCartItemMap());
        cart.setCartValue(calculateCartTotalValue(cartItemMap.values()));
        cart.setTotalDiscount(calculateCartTotalDiscount(cartItemMap.values()));
        cart.setCartItemMap(cartItemMap);
        return cart;
    }

    private Double calculateCartTotalValue(Collection<CartItem> cartItems) {
        return cartItems.stream().mapToDouble(l -> l.getGrandCost()).sum();
    }

    private Double calculateCartTotalDiscount(Collection<CartItem> cartItems) {
        return cartItems.stream().mapToDouble(l -> l.getGrandDiscount()).sum();
    }

    private Map<String,CartItem> validateCartItems(Map<String, CartItem> cartItemMap) throws NoSuchProductInStore, InsufficientQuantityException, ProductNotFoundException {
        for(String productId : cartItemMap.keySet()){
            CartItem cartItem = validateIndividualProduct(cartItemMap.get(productId));
            cartItemMap.put(productId,cartItem);
        }
        return cartItemMap;
    }

    private CartItem validateIndividualProduct(CartItem cartItem) throws InsufficientQuantityException, ProductNotFoundException {
        InventoryItem inventoryItem = inventoryService.getInventoryDetailForProduct(cartItem.getProduct().getProductId());
        if(inventoryItem.getProductQuantity()>= cartItem.getProductQty()){
            ProductDiscount productDiscount = discountService.getDiscountForProduct(cartItem.getProduct().getProductId());
            if(productDiscount!=null && !CollectionUtils.isEmpty(productDiscount.getDiscounts())) {
                CalculationUtil.populateDiscounts(productDiscount.getDiscounts(),cartItem);
            }
            InventoryItemRequest inventoryItemRequest = InventoryItemRequest.builder()
                    .productId(cartItem.getProduct().getProductId())
                    .productQuantity(-1 * cartItem.getProductQty())
                    .build();
            inventoryService.updateInventoryItemQuantity(inventoryItemRequest);
        }else{
            throw new InsufficientQuantityException("The product with product Id "+ cartItem.getProduct()+" does not have sufficient quantity Please decrease desired quantity");
        }
        return cartItem;
    }
}
