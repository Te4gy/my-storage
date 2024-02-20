package com.storage.mystorage.myRepositories;

import com.storage.mystorage.myEntitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String productName);
    Optional<Product> findAllByStorageId(Long storageId);
}
