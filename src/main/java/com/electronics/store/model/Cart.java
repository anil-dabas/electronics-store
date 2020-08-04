package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "cart")
@Table(name = "cart",uniqueConstraints = @UniqueConstraint(name = "uc_userId", columnNames = { "userId" }))
public class Cart {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid",strategy = "uuid")
    private String cartId;
    @Column(nullable = false)
    @NotNull(message = "UserId cannot be Null!")
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "productId")
    Map<String, CartItem> cartItemMap;
    Double cartValue;
    Double totalDiscount;
}
