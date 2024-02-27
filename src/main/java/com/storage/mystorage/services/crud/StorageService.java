package com.storage.mystorage.services.crud;

import com.storage.mystorage.utils.dto.answersDto.StorageDto;
import com.storage.mystorage.entities.Product;
import com.storage.mystorage.entities.Storage;
import com.storage.mystorage.repositories.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    final StorageRepository storageRepository;

    public Storage saveStorage(Storage storage) {
        storageRepository.save(storage);
        return storage;
    }

    public StorageDto saveProductsToStorage(Long storageId, List<Product> productList) {
//        Storage storage = findStorageById(storageId);
//        storage.setProductList(productList);
//        for (Product product : productList) {
//            product.setStorage(storage);
//        }
//        Storage updatedStorage = storageRepository.save(storage);
//        return StorageProductConvertor.toStorageDto(updatedStorage);
        return null;
    }

    public Storage findStorageById(Long id) {
        return storageRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Storage not exists"));
    }


    public List<Storage> findAllStorages(){
        return storageRepository.findAll();
    }
}
