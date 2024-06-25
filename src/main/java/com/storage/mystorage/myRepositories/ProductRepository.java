package com.storage.mystorage.myRepositories;

import com.storage.mystorage.myEntitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
