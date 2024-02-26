package com.storage.mystorage.services;

import com.storage.mystorage.services.EntityRepos.StorageService;
import com.storage.mystorage.utils.myDto.answersDto.ProductDto;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsService {

    final StorageService storageService;

    public List<StorageDto> allProducts() {
        List<Storage> allStorages = storageService.findAllStorages();
        return allStorages.stream()
                .map(StorageProductConvertor::toStorageDto)
                .toList();
    }


    public List<StorageDto> inStockProducts() {
        List<Storage> allStorages = storageService.findAllStorages();
        List<StorageDto> allStoragesDto = allStorages.stream()
                .map(StorageProductConvertor::toStorageDto).toList();

        return allStoragesDto.stream()
                .map(storageDto -> {
                    StorageDto filtredStorageDto = new StorageDto();
                    filtredStorageDto.setId(storageDto.getId());
                    filtredStorageDto.setName(storageDto.getName());

                    List<ProductDto> filteredProductsDto = storageDto.getProductList().stream()
                            .filter(ProductDto::isExists)
                            .toList();
                    filtredStorageDto.setProductList(filteredProductsDto);
                    return filtredStorageDto;
                }).toList();
    }
}
