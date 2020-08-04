package com.electronics.store.service.cart;

import com.electronics.store.data.CartRepository;
import com.electronics.store.domainvalue.DiscountType;
import com.electronics.store.exception.InsufficientQuantityException;
import com.electronics.store.exception.NoSuchCartExist;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.*;
import com.electronics.store.request.CartRequest;
import com.electronics.store.service.discount.DiscountService;
import com.electronics.store.service.inventory.InventoryService;
import com.electronics.store.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceTest {

    private static final String USER_ID = "USER_1234";
    private static final Integer PRODUCT_QTY = 4;
    private static final String PRODUCT_ID = "1234";
    private static final String PRODUCT_NAME = "DUMMY PRODUCT";
    private static final String PRODUCT_DESCRIPTION = "This is the dummy description of the product";
    private static final Double PRODUCT_PRICE = 20.0;
    private static final String INVENTORY_ID ="INV_1234" ;
    private static final String PRODUCT_DISCOUNT_ID = "PD_1234";
    private static final ZonedDateTime dateExpired = ZonedDateTime.parse("2020-09-09T10:15:30+08:00");
    private static final Integer MINIMUM_QTY = 2;
    private static final Integer DISCOUNT_PERCENTAGE = 20;
    private static final String DISCOUNT_ID = "DISCOUNT_01";
    private static final Double TOTAL_DISCOUNT = 8.0;
    private static final Double CART_VALUE = 40.0;
    private static final String CART_ID = "CART_1234";
    private static final String CART_ITEM_ID ="CART_ITEM_1234" ;
    @Autowired
    CartService cartService;

    @MockBean
    CartRepository cartRepository;
    @MockBean
    DiscountService discountService;

    @MockBean
    ProductService productService;

    @MockBean
    InventoryService inventoryService;

    @Test
    void getCartByCartId() {
    }

    @Test
    void addToCart() throws ProductNotFoundException, NoSuchProductInStore, InsufficientQuantityException, NoSuchCartExist {
        Product product = getProduct();
        InventoryItem inventoryItem = getInventoryItem();
        ProductDiscount productDiscount = getProductDiscount();
        Cart newCart = Cart.builder().userId(USER_ID).cartValue(CART_VALUE).cartId(CART_ID).totalDiscount(TOTAL_DISCOUNT).build();

        when(inventoryService.getInventoryDetailForProduct(anyString())).thenReturn(inventoryItem);
        when(discountService.getDiscountForProduct(anyString())).thenReturn(productDiscount);
        when(productService.getProductById(anyString())).thenReturn(product);
        when(cartRepository.save(any())).thenReturn(newCart);
        CartRequest cartRequest = CartRequest.builder().productId(PRODUCT_ID)
                .userId(USER_ID).productQuantity(PRODUCT_QTY).build();

        Cart cart = cartService.addToCart(cartRequest);
        assertNotNull(cart);
        assertEquals(cart.getCartValue(),CART_VALUE);
    }


    @Test
    void removeFromCart() {
    }

    @Test
    void getCartItems() throws NoSuchCartExist {
        Map<String,CartItem> cartItemsMap = new HashMap<>();
        CartItem cartItem = CartItem.builder().cartItemId(CART_ITEM_ID).build();
        cartItemsMap.put(PRODUCT_ID,cartItem);
        Cart newCart = Cart.builder().userId(USER_ID).cartValue(CART_VALUE).cartId(CART_ID).cartItemMap(cartItemsMap).totalDiscount(TOTAL_DISCOUNT).build();
        when(cartRepository.findById(anyString())).thenReturn(Optional.of(newCart));
        List<CartItem> returnedCartItems = cartService.getCartItems(CART_ID);
        assertNotNull(returnedCartItems);
    }

    private Product getProduct() {
        return Product.builder().productId(PRODUCT_ID).productPrice(PRODUCT_PRICE)
                .productDescription(PRODUCT_DESCRIPTION).productName(PRODUCT_NAME).build();
    }

    private ProductDiscount getProductDiscount() {
        List<Discount> discounts = new ArrayList<>();
        Discount discount = Discount.builder().discountId(DISCOUNT_ID).dateExpired(dateExpired)
                .discountType(DiscountType.PERCENTAGE).minimumQty(MINIMUM_QTY).discountPercent(DISCOUNT_PERCENTAGE)
                .dateCreated(ZonedDateTime.now()).build();
        discounts.add(discount);
        return ProductDiscount.builder()
                .productDiscountId(PRODUCT_DISCOUNT_ID).productId(PRODUCT_ID)
                .discounts(discounts).build();
    }

    private InventoryItem getInventoryItem() {
        return InventoryItem.builder().inventoryId(INVENTORY_ID)
                .productId(PRODUCT_ID).productQuantity(PRODUCT_QTY).build();
    }
}