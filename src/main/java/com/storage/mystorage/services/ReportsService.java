package com.storage.mystorage.services;

import com.storage.mystorage.myDto.answersDto.ProductDto;
import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsService {

    final StorageService storageService;

    public List<ProductDto> allProducts(){
        List<Storage> allStorages = storageService.findAllStorages();
        List<Product> allProducts = allStorages.stream()
                .map(Storage::getProductList)
                .flatMap(Collection::stream)
                .toList();
        return StorageProductConvertor.toProductDtoList(allProducts);
    }


    public List<StorageDto> productsInStock(){
        List<Storage> allStorages = storageService.findAllStorages();
        List<StorageDto> allStoragesDto = allStorages.stream()
                .map(StorageProductConvertor::toStorageDto).toList();

        List<StorageDto> productsInStock = allStoragesDto.stream()
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
        return productsInStock;
    }
}
