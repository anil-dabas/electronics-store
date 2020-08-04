package com.electronics.store.service.discount;

import com.electronics.store.exception.InvalidDiscountTypeException;
import com.electronics.store.exception.NoDiscountFoundForProduct;
import com.electronics.store.exception.NoDiscountWithCriteriaException;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.model.ProductDiscount;
import com.electronics.store.request.CreateDiscountRequest;
import com.electronics.store.request.UpdateDiscountRequest;

import java.util.List;

public interface DiscountService {

    ProductDiscount createNewDiscount(CreateDiscountRequest createDiscountRequest) throws NoSuchProductInStore;
    List<ProductDiscount> getAllDiscounts();
    ProductDiscount getDiscountForProduct(String productId);
    ProductDiscount updateDiscountRequest(UpdateDiscountRequest updateDiscountRequest) throws NoDiscountFoundForProduct, NoDiscountWithCriteriaException, InvalidDiscountTypeException, NoSuchProductInStore;
    Boolean deleteDiscount(String discountId) throws NoDiscountFoundForProduct;
}
