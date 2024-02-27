package com.storage.mystorage.services;

import com.storage.mystorage.services.crud.StorageService;
import com.storage.mystorage.utils.dto.answersDto.ProductDto;
import com.storage.mystorage.utils.dto.answersDto.StorageDto;
import com.storage.mystorage.entities.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsService {

    final StorageService storageService;
    private final StorageProductConverter converter;

    public List<StorageDto> allProducts() {
        List<Storage> allStorages = storageService.findAllStorages();
        return allStorages.stream()
                .map(converter::toStorageDto)
                .toList();
    }


    public List<StorageDto> inStockProducts() {
        List<Storage> allStorages = storageService.findAllStorages();
        List<StorageDto> allStoragesDto = allStorages.stream()
                .map(converter::toStorageDto).toList();

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
