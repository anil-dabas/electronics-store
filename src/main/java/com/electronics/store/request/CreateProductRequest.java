package com.electronics.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateProductRequest {

    private String productName;
    private String productDescription;
    private Double productPrice;
}
