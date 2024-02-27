package com.storage.mystorage.services.crud;

import com.storage.mystorage.entities.Product;
import com.storage.mystorage.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public Product getById(Long productId) {
        return productRepository.getById(productId);
    }

    public Product getByName(String name) {
        return productRepository.getByName(name);
    }
}
