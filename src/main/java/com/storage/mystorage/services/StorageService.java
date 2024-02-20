package com.storage.mystorage.services;

import com.storage.mystorage.myDto.answersDto.ProductDto;
import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.myRepositories.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {

    final StorageRepository storageRepository;

    public Storage saveStorage(Storage storage) {
        storageRepository.save(storage);
        return storage;
    }

    public StorageDto saveProductsToStorage(Long storageId, List<Product> productList) {
        Optional<Storage> optStorage = storageRepository.findById(storageId);
        Storage storage = optStorage.orElseThrow(RuntimeException::new);
        storage.setProductList(productList);
        for (Product product : productList) {
            product.setStorage(storage);
        }
        Storage updatedStorage = storageRepository.save(storage);
        return StorageProductConvertor.toStorageDto(updatedStorage);
    }

    public Storage findStorageById(Long id) {
        return storageRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    public List<Storage> findAllStorages(){
        return storageRepository.findAll();
    }
}
