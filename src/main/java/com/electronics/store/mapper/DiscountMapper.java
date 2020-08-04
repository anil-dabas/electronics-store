package com.electronics.store.mapper;

import com.electronics.store.model.ProductDiscount;
import com.electronics.store.model.ProductDiscountDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountMapper {

    public static ProductDiscountDTO convertToDiscountDTO(ProductDiscount productDiscount) {
        return ProductDiscountDTO.builder().productDiscountId(productDiscount.getProductDiscountId())
                .discounts(productDiscount.getDiscounts())
                .productId(productDiscount.getProductId()).build();
    }

    public static List<ProductDiscountDTO> convertToDiscountDTOs(List<ProductDiscount> productDiscounts) {
        return productDiscounts.stream().map(DiscountMapper::convertToDiscountDTO).collect(Collectors.toList());
    }
}
