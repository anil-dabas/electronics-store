package com.electronics.store.resource;

import com.electronics.store.exception.*;
import com.electronics.store.model.Cart;
import com.electronics.store.model.CartItem;
import com.electronics.store.model.Product;
import com.electronics.store.request.CartRequest;
import com.electronics.store.service.cart.CartServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartResource.class)
@AutoConfigureMockMvc(addFilters = false)
class CartResourceTest {


    private static final String USER_ID = "USER1234";
    private static final String PRODUCT_ID = "1234";
    private static final Integer PRODUCT_QUANTITY = 2 ;
    private static final String CART_ID = "CART1234" ;
    private static final String PRODUCT_NAME = "DUMMY PRODUCT";
    private static final String PRODUCT_DESCRIPTION = "This is the dummy description of the product";
    private static final Double PRODUCT_PRICE = 20.0;
    private static final String CART_ITEM_ID = "CART_ITEM_1234";
    private static final Double GRAND_COST = 40.0;
    private static final Double GRAND_DISCOUNT = 0.0;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CartServiceImpl cartService;

    @Test
    void getCartItems() {
    }

    @Test
    void addToCartPositive() throws Exception {
        Cart cart = getCart();
        CartRequest cartRequest = getCartRequest();
        when(cartService.addToCart(any())).thenReturn(cart);
        String cartRequestJson = new ObjectMapper().writeValueAsString(cartRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/cart")
                .content(cartRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartId").isNotEmpty());

    }

    @Test
    void addToCartProductNotFoundException() throws Exception {
        CartRequest cartRequest = getCartRequest();
        cartRequest.setProductId("FAKE_PRODUCT");
        when(cartService.addToCart(any())).thenThrow(ProductNotFoundException.class);
        Throwable exception = assertThrows(ProductNotFoundException.class, () -> cartService.addToCart(cartRequest));
        assertNotNull(exception);
    }
    @Test
    void addToCartInsufficientQuantityException() throws Exception{
        CartRequest cartRequest = getCartRequest();
        when(cartService.addToCart(any())).thenThrow(InsufficientQuantityException.class);
        Throwable exception = assertThrows(InsufficientQuantityException.class, () -> cartService.addToCart(cartRequest));
        assertNotNull(exception);
    }
    @Test
    void updateItemQuantityPositive() throws Exception {
        Cart cart = getCart();
        CartRequest cartRequest = getCartRequest();
        when(cartService.updateItemQuantity(any())).thenReturn(cart);
        String cartRequestJson = new ObjectMapper().writeValueAsString(cartRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/cart")
                .content(cartRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").isNotEmpty());
    }

    @Test
    void updateItemQuantityProductNotInCartException() throws Exception {
        CartRequest cartRequest = getCartRequest();
        cartRequest.setProductId("PRODUCT_5678");// Product with Id PRODUCT_123456 is Not added in the cart
        when(cartService.updateItemQuantity(any())).thenThrow(ProductNotInCartException.class);
        Throwable exception = assertThrows(ProductNotInCartException.class, () -> cartService.updateItemQuantity(cartRequest));
        assertNotNull(exception);
    }
    @Test
    void updateItemQuantityInsufficientQuantityException() throws Exception {
        CartRequest cartRequest = getCartRequest();
        cartRequest.setProductId("PRODUCT_5678");// Product with Id PRODUCT_123456 is Not added in the cart
        when(cartService.updateItemQuantity(any())).thenThrow(InsufficientQuantityException.class);
        Throwable exception = assertThrows(InsufficientQuantityException.class, () -> cartService.updateItemQuantity(cartRequest));
        assertNotNull(exception);
    }
    @Test
    void removeFromCartPositive() throws Exception {
        Cart cart = getCart();
        cart.setCartItemMap(new HashMap<>());
        when(cartService.removeFromCart(anyString(),anyString())).thenReturn(cart);
        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/"+PRODUCT_ID)
                .param("userId", USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").isNotEmpty());
    }

    @Test
    void removeFromCartProductNotInCart() throws Exception{
        when(cartService.removeFromCart(anyString(),anyString())).thenThrow(ProductNotInCartException.class);
        Throwable exception = assertThrows(ProductNotInCartException.class, () -> cartService.removeFromCart("PRODUCT_5678",USER_ID));
        assertNotNull(exception);
    }

    @Test
    void removeFromCartCartIsEmpty() throws Exception{
        when(cartService.removeFromCart(anyString(),anyString())).thenThrow(CartIsEmptyException.class);
        Throwable exception = assertThrows(CartIsEmptyException.class, () -> cartService.removeFromCart("PRODUCT_5678","FAKE_USER"));
        assertNotNull(exception);
    }

    // Creating Test elements
    private CartRequest getCartRequest() {
        return CartRequest.builder()
                .userId(USER_ID).productId(PRODUCT_ID)
                .productQuantity(PRODUCT_QUANTITY).build();
    }
    private Cart getCart() {
        CartItem cartItem = createCartItem();
        Map<String,CartItem> cartItemMap = new HashMap<>();
        cartItemMap.put(PRODUCT_ID,cartItem);
        return Cart.builder().cartId(CART_ID)
                .cartItemMap(cartItemMap).build();
    }

    private CartItem createCartItem() {
        Product product = createProduct();
        return CartItem.builder().product(product).productQty(PRODUCT_QUANTITY)
                .cartItemId(CART_ITEM_ID).grandCost(GRAND_COST).grandDiscount(GRAND_DISCOUNT).build();
    }

    private Product createProduct() {
        return Product.builder().productId(PRODUCT_ID)
                .productName(PRODUCT_NAME)
                .productDescription(PRODUCT_DESCRIPTION)
                .productPrice(PRODUCT_PRICE).build();
    }
}