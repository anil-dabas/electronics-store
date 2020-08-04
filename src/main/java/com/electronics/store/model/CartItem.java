package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "cartItem")
@Table(name = "cartItem")
public class CartItem {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid",strategy = "uuid")
    private String cartItemId;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Product product;
    private Integer productQty;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Discount discountApplied;
    private Double grandCost;
    private Double grandDiscount;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> discountedProduct;
}
