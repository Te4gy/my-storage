package com.storage.mystorage.repositories;

import com.storage.mystorage.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Product getByName(String name);

}
