package com.storage.mystorage.allRepositories.entitysRepos;

import com.storage.mystorage.allEntitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
