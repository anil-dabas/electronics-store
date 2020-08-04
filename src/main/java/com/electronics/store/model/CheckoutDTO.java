package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CheckoutDTO {

    private String cartId;
    private Double cartValue;
    private Double totalDiscount;

}
