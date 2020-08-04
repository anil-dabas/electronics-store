package com.electronics.store.mapper;

import com.electronics.store.model.InventoryItem;
import com.electronics.store.model.InventoryItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryMapper {
    public static InventoryItemDTO convertToInventoryItemDTO(InventoryItem inventoryItem) {
        return InventoryItemDTO.builder().inventoryId(inventoryItem.getInventoryId())
                .productId(inventoryItem.getProductId())
                .productQuantity(inventoryItem.getProductQuantity()).build();
    }

    public static List<InventoryItemDTO> convertToInventoryItemDTOs(List<InventoryItem> inventoryItems) {
        return inventoryItems.stream().map(InventoryMapper::convertToInventoryItemDTO).collect(Collectors.toList());
    }
}
