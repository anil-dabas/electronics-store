package com.electronics.store.data;

import com.electronics.store.model.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<ProductDiscount,String> {
    Optional<ProductDiscount> findByProductId(String productId);
}
