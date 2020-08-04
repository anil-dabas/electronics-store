package com.electronics.store.service.checkout;

import com.electronics.store.exception.InsufficientQuantityException;
import com.electronics.store.exception.NoSuchCartExist;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.Cart;

public interface CheckoutService {
    Cart performCheckout(String cartId) throws NoSuchCartExist, NoSuchProductInStore, InsufficientQuantityException, ProductNotFoundException;
}
