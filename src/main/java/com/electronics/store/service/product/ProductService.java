package com.electronics.store.service.product;

import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.Product;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.PatchProductDescriptionRequest;
import com.electronics.store.request.PatchProductPriceRequest;

import java.util.List;

public interface ProductService {

    Product getProductById(String productId) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product create(CreateProductRequest createProductRequest);
    Boolean changeProductDescription(String productId, PatchProductDescriptionRequest patchRequest) throws ProductNotFoundException;
    Boolean changeProductPrice(String productId, PatchProductPriceRequest patchRequest) throws ProductNotFoundException;
    void deleteProduct(String productId) throws ProductNotFoundException;
}
