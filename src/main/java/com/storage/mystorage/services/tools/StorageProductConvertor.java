package com.storage.mystorage.services.tools;

import com.storage.mystorage.allEntitys.ProductConnection;
import com.storage.mystorage.utils.myDto.answersDto.ProductConnectionDto;
import com.storage.mystorage.utils.myDto.answersDto.ProductDto;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.allEntitys.Product;
import com.storage.mystorage.allEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StorageProductConvertor {

    public static ProductDto toProductDto(Product product, Long storageId, int amount) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPurchasePrice(product.getPurchasePrice());
        productDto.setSellPrice(product.getSellPrice());
        productDto.setAmount(amount);
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
//                    .peek(productConnection -> {int amount = productConnection.getAmount();})
//                    .map(ProductConnection::getProduct)
//                    .map(product -> StorageProductConvertor.toProductDto(product, storageId, amount))

                    .map(productConnection -> {
                        int amount = productConnection.getAmount();
                        Product product = productConnection.getProduct();
                        return StorageProductConvertor.toProductDto(product, storageId, amount);})
                    .toList();
            storageDto.setProductList(productDtoList);
        }
        return storageDto;
    }

    public static ProductConnectionDto toProductConnectionDto(ProductConnection productConnection){
        ProductConnectionDto productConnectionDto = new ProductConnectionDto();
        Long productConnectionId = productConnection.getId();
        int amount = productConnection.getAmount();
        Long storageId = productConnection.getStorage().getId();
        Product product = productConnection.getProduct();
        ProductDto productDto = toProductDto(product, storageId, amount);
        productDto.setAmount(amount);
        Storage storage = productConnection.getStorage();
        StorageDto storageDto = toStorageDto(storage);
        productConnectionDto.setId(productConnectionId);
        productConnectionDto.setStorage(storageDto);
        productConnectionDto.setProduct(productDto);
        return productConnectionDto;
    }

    public static StorageDto fromProductConnectionTotoStorageDtoAnswer(ProductConnection productConnection){
        Storage storage = productConnection.getStorage();
        Product product = productConnection.getProduct();
        product.setProductConnectionList(List.of(productConnection));
        productConnection.setProduct(product);
        storage.setProductConnectionList(List.of(productConnection));
        return toStorageDto(storage);


    }
}
