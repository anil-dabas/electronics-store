package com.electronics.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryItemRequest {

    @NotNull
    String productId;
    Integer productQuantity;
}
