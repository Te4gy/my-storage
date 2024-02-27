package com.storage.mystorage.repositories;

import com.storage.mystorage.entities.ProductConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductConnectionRepository extends JpaRepository<ProductConnection, Long> {

    ProductConnection findByProductIdAndStorageId(Long productId, Long storageId);

    boolean existsByProductIdAndStorageId(Long productId, Long storageId);
}
