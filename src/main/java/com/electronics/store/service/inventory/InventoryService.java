package com.electronics.store.service.inventory;

import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.InventoryItem;
import com.electronics.store.request.InventoryItemRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryItem> getAllInventoryItems();
    InventoryItem createInventoryItem(InventoryItemRequest inventoryItemRequest);
    InventoryItem updateInventoryItemQuantity(InventoryItemRequest inventoryItemRequest) throws ProductNotFoundException;
    Boolean deleteInventoryItem(String productId) throws NoSuchProductInStore;
    InventoryItem getInventoryDetailForProduct(String productId) throws ProductNotFoundException;
}
