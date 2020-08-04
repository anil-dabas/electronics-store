package com.electronics.store.service.inventory;

import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.InventoryItem;
import com.electronics.store.model.Product;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.InventoryItemRequest;
import com.electronics.store.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryServiceTest {

    private static final String DUMMY_DESCRIPTION = "This is a dummy product used for Unit tests ";
    private static final Double DUMMY_PRICE = 20.0;
    private static final String DUMMY_PRODUCT = "Dummy Product";

    @Autowired
    ProductService productService;

    @Autowired
    InventoryService inventoryService;

    @Test
    void getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();
        assertNotNull(inventoryItems);
    }

    @Test
    void updateInventoryItemQuantity() throws ProductNotFoundException {
        Product product = createProduct();
        Integer quantity = 20;
        InventoryItemRequest inventoryItemRequest = InventoryItemRequest.builder()
                .productQuantity(quantity).productId(product.getProductId()).build();
        InventoryItem inventoryItem = inventoryService.updateInventoryItemQuantity(inventoryItemRequest);
        assertNotNull(inventoryItem);
        assertEquals(quantity,inventoryItem.getProductQuantity());
    }

    @Test
    void deleteInventoryItem() throws NoSuchProductInStore {
        Product product = createProduct();
        Boolean isDeleted = inventoryService.deleteInventoryItem(product.getProductId());
        assertEquals(Boolean.TRUE,isDeleted);
    }

    @Test
    void getInventoryDetailForProduct() throws ProductNotFoundException {
        Product product = createProduct();
        InventoryItem inventoryItem = inventoryService.getInventoryDetailForProduct(product.getProductId());
        assertNotNull(inventoryItem);
        assertEquals(inventoryItem.getProductId(),product.getProductId());
    }

    private Product createProduct(){
        CreateProductRequest createProductRequest = CreateProductRequest.builder().productName(DUMMY_PRODUCT)
                .productDescription(DUMMY_DESCRIPTION).productPrice(DUMMY_PRICE).build();
        return productService.create(createProductRequest);
    }
}