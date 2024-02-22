package com.storage.mystorage.services;

import com.storage.mystorage.myDto.answersDto.ProductDto;
import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;

import java.util.List;

public class StorageProductConvertor {

    public static ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setLastBuyPrice(product.getLastBuyPrice());
        productDto.setLastSellPrice(product.getLastSellPrice());
        productDto.setExists(product.isExists());
        return productDto;
    }

    public static StorageDto toStorageDto(Storage storage) {
        StorageDto storageDto = new StorageDto();
        storageDto.setId(storage.getId());
        storageDto.setName(storage.getName());
        if (storage.getProductList() != null) {
            List<ProductDto> productDtoList = storage.getProductList().stream()
                    .map((StorageProductConvertor::toProductDto))
                    .toList();
            storageDto.setProductList(productDtoList);
        }
        return storageDto;
    }
}
