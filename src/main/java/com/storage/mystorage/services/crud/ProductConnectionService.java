package com.storage.mystorage.services.crud;

import com.storage.mystorage.entities.ProductConnection;
import com.storage.mystorage.repositories.ProductConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductConnectionService {

    final ProductConnectionRepository productConnectionRepository;

    public boolean existsByProductIdAndStorageId(Long id, Long storageId) {
        return productConnectionRepository.existsByProductIdAndStorageId(id, storageId);
    }

    public ProductConnection findByProductIdAndStorageId(Long id, Long storageId) {
        return productConnectionRepository.findByProductIdAndStorageId(id, storageId);
    }

    public void save(ProductConnection byProductId) {
        productConnectionRepository.save(byProductId);
    }
}
