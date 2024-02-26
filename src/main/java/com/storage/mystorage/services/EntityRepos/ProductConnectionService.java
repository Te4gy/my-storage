package com.storage.mystorage.services.EntityRepos;

import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.ProductConnection;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.myRepositories.ProductConnectionRepository;
import com.storage.mystorage.myRepositories.ProductRepository;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductConnectionService {

    final ProductConnectionRepository productConnectionRepository;
    final ProductRepository productRepository;
    final StorageService storageService;

    public ProductConnection saveProductToStorageConnection(Long storageId, Product product, int amount){
        Storage storage = storageService.findStorageById(storageId);
        ProductConnection productConnection = new ProductConnection();
        productConnection.setProduct(product);
        productConnection.setStorage(storage);
        productConnection.setAmount(amount);
        product.addProductConnection(productConnection);
        productRepository.save(product);
        return productConnectionRepository.save(productConnection);
    }
}
