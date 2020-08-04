package com.electronics.store.service.checkout;

import com.electronics.store.service.cart.CartService;
import com.electronics.store.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutServiceTest {

    @Autowired
    CheckoutService checkoutService;
    @MockBean
    CartService cartService;

    @Test
    void performCheckout() {

    }
}