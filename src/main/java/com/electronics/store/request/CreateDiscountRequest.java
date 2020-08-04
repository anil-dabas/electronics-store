package com.electronics.store.request;

import com.electronics.store.domainvalue.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateDiscountRequest {

    private String productId;
    private DiscountType discountType;
    private Integer minimumQty;
    private Integer discountPercent;
    private String discountBundleProduct;
    private ZonedDateTime dateExpired;

}
