package com.electronics.store.mapper;

import com.electronics.store.model.Cart;
import com.electronics.store.model.CartDTO;

import java.util.List;

public class CartMapper {

    public static CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO =  CartDTO.builder().cartId(cart.getCartId())
                .cartValue(cart.getCartValue())
                .totalDiscount(cart.getTotalDiscount())
                .build();
        if(!cart.getCartItemMap().isEmpty()){
            cartDTO.setCartItemMap(cart.getCartItemMap());
        }
        return cartDTO;
    }
}
