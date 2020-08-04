package com.electronics.store.request;

import com.electronics.store.domainvalue.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateDiscountRequest {
    @NotNull(message = " Discount Id is a mandatory field")
    private String discountId;
    @NotNull(message = " Product Id is a mandatory field")
    private String productId;
    @NotNull(message = " Discount Type is a mandatory field")
    private DiscountType discountType;
    private Integer minimumQty;
    private Integer discountPercent;
    private String discountBundleProduct;
    private ZonedDateTime dateExpired;
}
