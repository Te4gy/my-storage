package com.storage.mystorage.services;

import com.storage.mystorage.myEntitys.ProductConnection;
import com.storage.mystorage.services.EntityRepos.ProductService;
import com.storage.mystorage.utils.myDto.answersDto.ProductDto;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StorageProductConvertor {

    public static ProductDto toProductDto(Product product, Long storageId) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setLastBuyPrice(product.getLastBuyPrice());
        productDto.setLastSellPrice(product.getLastSellPrice());
        productDto.setExists(product.isExists());
        //todo product не содержит ProductConnection, почему???
        List<ProductConnection> productConnectionList = product.getProductConnectionList();
        productConnectionList.stream()
                .filter(productConnection -> productConnection
                        .getStorage()
                        .getId()
                        .equals(storageId))
                .filter(productConnection -> productConnection
                        .getProduct()
                        .getId()
                        .equals(product.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return productDto;
    }

    public static StorageDto toStorageDto(Storage storage) {
        StorageDto storageDto = new StorageDto();
        Long storageId = storage.getId();
        storageDto.setId(storageId);
        storageDto.setName(storage.getName());

        if (storage.getProductConnectionList() != null){
            List<ProductDto> productDtoList = storage.getProductConnectionList().stream()
                    .map(ProductConnection::getProduct)
                    .map(product -> StorageProductConvertor.toProductDto(product, storageId))
                    .toList();
            storageDto.setProductList(productDtoList);
        }
        return storageDto;
    }
}
