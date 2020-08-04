package com.electronics.store.service.product;

import com.electronics.store.data.ProductRepository;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.Product;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.InventoryItemRequest;
import com.electronics.store.request.PatchProductDescriptionRequest;
import com.electronics.store.request.PatchProductPriceRequest;
import com.electronics.store.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Product create(CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .productName(createProductRequest.getProductName())
                .productPrice(createProductRequest.getProductPrice())
                .productDescription(createProductRequest.getProductDescription())
                .build();
        product = productRepository.save(product);
        InventoryItemRequest inventoryItemRequest = InventoryItemRequest.builder().productId(product.getProductId())
                .productQuantity(0)
                .build();
        inventoryService.createInventoryItem(inventoryItemRequest);
        return product;
    }

    @Override
    public Product getProductById(String productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Could not find the product with Id: " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean changeProductDescription(String productId, PatchProductDescriptionRequest patchRequest) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Could not find the product with product id : " + productId + "to patch"));
        product.setProductDescription(patchRequest.getProductDescription());
        productRepository.save(product);
        return Boolean.TRUE;
    }
    @Override
    public Boolean changeProductPrice(String productId, PatchProductPriceRequest patchRequest) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Could not find the product with product id : " + productId + "to patch"));
        product.setProductPrice(patchRequest.getProductPrice());
        productRepository.save(product);
        return Boolean.TRUE;
    }
    @Override
    public void deleteProduct(String productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Could not found the product with product id: " + productId + " to delete"));
        productRepository.delete(product);
    }
}
