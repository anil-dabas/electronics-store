package com.electronics.store.service.inventory;

import com.electronics.store.data.InventoryRepository;
import com.electronics.store.data.ProductRepository;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.InventoryItem;
import com.electronics.store.model.Product;
import com.electronics.store.request.InventoryItemRequest;
import com.electronics.store.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductService productRepository;

    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    @Override
    public InventoryItem createInventoryItem(InventoryItemRequest inventoryItemRequest) {
        InventoryItem inventoryItem =  InventoryItem.builder()
                .productId(inventoryItemRequest.getProductId())
                .productQuantity(inventoryItemRequest.getProductQuantity())
                .build();
        return inventoryRepository.save(inventoryItem);
    }

    @Override
    public InventoryItem updateInventoryItemQuantity(InventoryItemRequest inventoryItemRequest) throws ProductNotFoundException {
        InventoryItem inventoryItem = getInventoryDetailForProduct(inventoryItemRequest.getProductId());
        inventoryItem.setProductQuantity(inventoryItem.getProductQuantity()+inventoryItemRequest.getProductQuantity());
        return inventoryRepository.save(inventoryItem);
    }

    @Override
    public Boolean deleteInventoryItem(String productId) throws NoSuchProductInStore {
        InventoryItem inventoryItem = inventoryRepository.findByProductId(productId).orElseThrow(() -> new NoSuchProductInStore("There is no product with Product Id " + productId));
        inventoryRepository.delete(inventoryItem);
        return Boolean.TRUE;
    }

    @Override
    public InventoryItem getInventoryDetailForProduct(String productId) throws ProductNotFoundException {
        return  inventoryRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException("There is no product with Product Id "+productId));
    }
}
