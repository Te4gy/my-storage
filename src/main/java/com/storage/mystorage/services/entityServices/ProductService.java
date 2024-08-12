package com.storage.mystorage.services.entityServices;

import com.storage.mystorage.allEntitys.Product;
import com.storage.mystorage.allRepositories.entitysRepos.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;

    public Product saveProduct(Product product){
            return productRepository.save(product);
    }

    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Storage not exists"));
    }
}
