package com.electronics.store.model;

import com.electronics.store.domainvalue.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "discount")
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid",strategy = "uuid")
    private String discountId;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer minimumQty;
    private Integer discountPercent;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Product discountBundleProduct;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();
    private ZonedDateTime dateExpired;

}
