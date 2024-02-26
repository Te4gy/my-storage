package com.storage.mystorage.services.EntityRepos;

import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.ProductConnection;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.myRepositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }
}
