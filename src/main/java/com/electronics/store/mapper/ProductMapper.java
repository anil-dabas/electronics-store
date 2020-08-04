package com.electronics.store.mapper;

import com.electronics.store.model.Product;
import com.electronics.store.model.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder().productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .build();

    }

    public static List<ProductDTO> convertToProductDTOList(List<Product> allProducts) {
        return allProducts.stream().map(ProductMapper::convertToProductDTO).collect(Collectors.toList());
    }
}
