package com.electronics.store.service.cart;

import com.electronics.store.exception.*;
import com.electronics.store.model.Cart;
import com.electronics.store.model.CartItem;
import com.electronics.store.request.CartRequest;

import java.util.List;

public interface CartService {

    Cart getCartByCartId(String cartId) throws NoSuchCartExist;
    Cart addToCart(CartRequest addCartRequest) throws NoSuchProductInStore, InsufficientQuantityException, ProductNotFoundException;
    Cart removeFromCart(String productId,String userId) throws CartIsEmptyException, ProductNotInCartException;
    List<CartItem> getCartItems(String cartId) throws NoSuchCartExist;
    Cart updateItemQuantity(CartRequest cartRequest) throws ProductNotInCartException, CartIsEmptyException, InsufficientQuantityException, ProductNotFoundException;
}
