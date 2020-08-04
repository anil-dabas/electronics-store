package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InventoryItemDTO {

    String inventoryId;
    String productId;
    Integer productQuantity;
}
