package com.electronics.store.resource;

import com.electronics.store.exception.InsufficientQuantityException;
import com.electronics.store.exception.NoSuchCartExist;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.Cart;
import com.electronics.store.model.CheckoutDTO;
import com.electronics.store.service.checkout.CheckoutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutResource {

    private final CheckoutService checkoutService;
    private ModelMapper modelMapper;

    @Autowired
    public CheckoutResource(CheckoutService checkoutService, ModelMapper modelMapper) {
        this.checkoutService = checkoutService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{cart_Id}")
    public ResponseEntity<CheckoutDTO> performCheckout(@PathVariable(value = "cart_Id" )String cartId) throws NoSuchCartExist, NoSuchProductInStore, InsufficientQuantityException, ProductNotFoundException {
        Cart cart = checkoutService.performCheckout(cartId);
        CheckoutDTO checkoutDTO = modelMapper.map(cart,CheckoutDTO.class);
        return new ResponseEntity<>(checkoutDTO, HttpStatus.OK);
    }
}
