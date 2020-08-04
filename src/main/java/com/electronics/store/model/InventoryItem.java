package com.electronics.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "inventory")
@Table(name = "inventory",uniqueConstraints = @UniqueConstraint(name = "uc_productId", columnNames = { "productId" }))
public class InventoryItem {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid",strategy = "uuid")
    String inventoryId;
    String productId;
    Integer productQuantity;
}
