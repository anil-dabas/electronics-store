package com.electronics.store.resource;

import com.electronics.store.exception.InvalidDiscountTypeException;
import com.electronics.store.exception.NoDiscountFoundForProduct;
import com.electronics.store.exception.NoDiscountWithCriteriaException;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.mapper.DiscountMapper;
import com.electronics.store.model.ProductDiscountDTO;
import com.electronics.store.request.CreateDiscountRequest;
import com.electronics.store.request.UpdateDiscountRequest;
import com.electronics.store.service.discount.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountResource {

    private final DiscountService discountService;
    @Autowired
    DiscountResource(DiscountService discountService){
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDiscountDTO>> getAllDiscounts(){
        return new ResponseEntity<>(DiscountMapper.convertToDiscountDTOs(discountService.getAllDiscounts()),HttpStatus.OK);
    }

    @GetMapping("/product_Id")
    public ResponseEntity<ProductDiscountDTO> getDiscountForProduct(@PathVariable(value = "product_Id") String productId){
        return new ResponseEntity<>(DiscountMapper.convertToDiscountDTO(discountService.getDiscountForProduct(productId)),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductDiscountDTO> createNewDiscount(@RequestBody CreateDiscountRequest createDiscountRequest) throws NoSuchProductInStore {
        return new ResponseEntity<>(DiscountMapper.convertToDiscountDTO(discountService.createNewDiscount(createDiscountRequest)), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<ProductDiscountDTO> updateDiscount(UpdateDiscountRequest updateDiscountRequest) throws NoDiscountWithCriteriaException, InvalidDiscountTypeException, NoDiscountFoundForProduct, NoSuchProductInStore {
        return new ResponseEntity<>(DiscountMapper.convertToDiscountDTO(discountService.updateDiscountRequest(updateDiscountRequest)),HttpStatus.OK);
    }
    @DeleteMapping("/discount_Id")
    public ResponseEntity<Boolean> deleteDiscount(@PathVariable(value = "discount_Id") String discountId) throws NoDiscountFoundForProduct {
        return new ResponseEntity<>(discountService.deleteDiscount(discountId),HttpStatus.OK);
    }

}
