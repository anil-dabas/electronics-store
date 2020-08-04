package com.electronics.store.data;

import com.electronics.store.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem,String> {
    Optional<InventoryItem> findByProductId(String productId);
}
