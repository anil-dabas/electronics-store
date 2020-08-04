package com.electronics.store.resource;

import com.electronics.store.exception.*;
import com.electronics.store.mapper.CartMapper;
import com.electronics.store.model.CartDTO;
import com.electronics.store.model.CartItem;
import com.electronics.store.request.CartRequest;
import com.electronics.store.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartResource {

    private final CartService cartService;
    @Autowired
    CartResource(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(String cartId) throws NoSuchCartExist {
        return new ResponseEntity<>(cartService.getCartItems(cartId),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartRequest cartRequest) throws InsufficientQuantityException, ProductNotFoundException, NoSuchProductInStore {
        return new ResponseEntity<>(CartMapper.convertToCartDTO(cartService.addToCart(cartRequest)), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<CartDTO> updateItemQuantity(@RequestBody CartRequest cartRequest) throws CartIsEmptyException, ProductNotInCartException, InsufficientQuantityException, ProductNotFoundException {
        return new ResponseEntity<>(CartMapper.convertToCartDTO(cartService.updateItemQuantity(cartRequest)),HttpStatus.OK);
    }
    @DeleteMapping("/{product_Id}")
    public ResponseEntity<CartDTO> removeFromCart(@PathVariable(value = "product_Id") String productId ,@RequestParam(value = "userId") String userId) throws CartIsEmptyException, ProductNotInCartException {
        return new ResponseEntity<>(CartMapper.convertToCartDTO(cartService.removeFromCart(productId,userId)),HttpStatus.OK);
    }

}
