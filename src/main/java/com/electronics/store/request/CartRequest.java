package com.electronics.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartRequest {
    private String userId;
    private String productId;
    private Integer productQuantity;
}
