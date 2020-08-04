package com.electronics.store.service.cart;

import com.electronics.store.data.CartRepository;
import com.electronics.store.exception.*;
import com.electronics.store.model.*;
import com.electronics.store.request.CartRequest;
import com.electronics.store.service.discount.DiscountService;
import com.electronics.store.service.inventory.InventoryService;
import com.electronics.store.service.product.ProductService;
import com.electronics.store.util.CalculationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private ProductService productService;

    @Override
    public Cart getCartByCartId(String cartId) throws NoSuchCartExist {
        return cartRepository.findById(cartId).orElseThrow(()-> new NoSuchCartExist("There is No Cart with Cart Id " + cartId));
    }

    @Override
    public Cart addToCart(CartRequest cartRequest) throws InsufficientQuantityException, ProductNotFoundException {
        Cart newCart = Cart.builder().cartItemMap(new HashMap<>()).userId(cartRequest.getUserId()).build();
        Cart cart = cartRepository.findByUserId(cartRequest.getUserId()).orElse(newCart);
        Map<String, CartItem> cartMap = cart.getCartItemMap();
        InventoryItem inventoryItem = inventoryService.getInventoryDetailForProduct(cartRequest.getProductId());
        if(inventoryItem.getProductQuantity()<cartRequest.getProductQuantity()){
            throw new InsufficientQuantityException("The product with product Id "+ cartRequest.getProductId()+" does not have sufficient quantity Please decrease desired quantity");
        }
        CartItem cartItem = getCartItem(cartRequest, cartMap, inventoryItem);
        populateCartCosts(cartRequest, cartItem);
        cartMap.put(cartRequest.getProductId(),cartItem);
        cart.setCartItemMap(cartMap);
        return cartRepository.save(cart);
    }

    private void populateCartCosts(CartRequest cartRequest, CartItem cartItem) {
        ProductDiscount productDiscount = discountService.getDiscountForProduct(cartRequest.getProductId());
        if(productDiscount != null && !CollectionUtils.isEmpty(productDiscount.getDiscounts())) {
            CalculationUtil.populateDiscounts(productDiscount.getDiscounts(),cartItem);
            CalculationUtil.populateCosts(cartItem);
        }
    }

    private CartItem getCartItem(CartRequest cartRequest, Map<String, CartItem> cartMap, InventoryItem inventoryItem) throws InsufficientQuantityException, ProductNotFoundException {
        CartItem cartItem;
        if(cartMap.containsKey(cartRequest.getProductId())){
            cartItem = cartMap.get(cartRequest.getProductId());
            Integer newQuantity = cartItem.getProductQty()+cartRequest.getProductQuantity();
            if(newQuantity<=inventoryItem.getProductQuantity()) {
                cartItem.setProductQty(newQuantity);
            }else {
                throw new InsufficientQuantityException("The product with product Id "+ cartRequest.getProductId()+" does not have sufficient quantity Please decrease desired quantity");
            }
        }else{
            Product product = productService.getProductById(cartRequest.getProductId());
            cartItem = CartItem.builder()
                    .product(product)
                    .productQty(cartRequest.getProductQuantity())
                    .build();
        }
        return cartItem;
    }

    @Override
    public Cart removeFromCart(String productId,String userId) throws CartIsEmptyException, ProductNotInCartException {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()-> new CartIsEmptyException("The Cart is Empty"));
        Map<String,CartItem> cartItemMap = cart.getCartItemMap();
        if(!cartItemMap.containsKey(productId)){
            throw new ProductNotInCartException("The requested Product is not available in the cart");
        }else{
            cartItemMap.remove(productId);
        }
        cart.setCartItemMap(cartItemMap);
        return cartRepository.save(cart);
    }

    @Override
    public List<CartItem> getCartItems(String cartId) throws NoSuchCartExist {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new NoSuchCartExist("There is no cart with cart Id " + cartId));
        return new ArrayList<>(cart.getCartItemMap().values());
    }

    @Override
    public Cart updateItemQuantity(CartRequest cartRequest) throws ProductNotInCartException, CartIsEmptyException, InsufficientQuantityException, ProductNotFoundException {
        Cart cart = cartRepository.findByUserId(cartRequest.getUserId()).orElseThrow(()-> new CartIsEmptyException("The Cart is Empty"));
        Map<String,CartItem> cartItemMap = cart.getCartItemMap();
        if(!cartItemMap.containsKey(cartRequest.getProductId())){
            throw new ProductNotInCartException("The requested Product is not available in the cart");
        }else{
            CartItem cartItem = cartItemMap.get(cartRequest.getProductId());
            InventoryItem inventoryItem = inventoryService.getInventoryDetailForProduct(cartRequest.getProductId());
            Integer newQuantity = cartItem.getProductQty()+cartRequest.getProductQuantity();
            if(newQuantity<=inventoryItem.getProductQuantity()) {
                cartItem.setProductQty(newQuantity);
            }else {
                throw new InsufficientQuantityException("The product with product Id "+ cartRequest.getProductId()+" does not have sufficient quantity Please decrease desired quantity");
            }
            if(cartItem.getProductQty()<1){
                cartItemMap.remove(cartRequest.getProductId());
            }else{
                populateCartCosts(cartRequest,cartItem);
                cartItemMap.put(cartRequest.getProductId(),cartItem);
            }
        }
        cart.setCartItemMap(cartItemMap);
        return cartRepository.save(cart);
    }
}
