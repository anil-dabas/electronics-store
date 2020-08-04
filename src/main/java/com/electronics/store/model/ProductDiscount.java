package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name ="productDiscount")
@Table(name = "productDiscount")
public class ProductDiscount {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid",strategy = "uuid")
    private String productDiscountId;
    private String productId;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Discount> discounts;
}

