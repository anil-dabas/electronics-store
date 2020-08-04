package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartDTO {

    private String cartId;
    private String userId;
    Map<String, CartItem> cartItemMap;
    Double cartValue;
    Double totalDiscount;
}
