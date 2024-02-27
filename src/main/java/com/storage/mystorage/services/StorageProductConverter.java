package com.storage.mystorage.services;

import com.storage.mystorage.entities.ProductConnection;
import com.storage.mystorage.services.crud.ProductService;
import com.storage.mystorage.utils.dto.answersDto.ProductDto;
import com.storage.mystorage.utils.dto.answersDto.StorageDto;
import com.storage.mystorage.entities.Product;
import com.storage.mystorage.entities.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StorageProductConverter {

    private final ProductService productService;

    public ProductDto toProductDto(Long productId, Long storageId) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        Product product = productService.getById(productId);
        productDto.setName(product.getName());
        productDto.setLastBuyPrice(product.getLastBuyPrice());
        productDto.setLastSellPrice(product.getLastSellPrice());
        productDto.setExists(product.isExists());
        //todo product не содержит ProductConnection, почему???
        List<ProductConnection> productConnectionList = product.getProductConnectionList();
        int sum = productConnectionList.stream()
                .filter(con -> con.getStorageId().equals(storageId))
                .mapToInt(ProductConnection::getAmount)
                .sum();
        productDto.setAmount(sum);
        return productDto;
    }

    public StorageDto toStorageDto(Storage storage) {
        StorageDto storageDto = new StorageDto();
        Long storageId = storage.getId();
        storageDto.setId(storageId);
        storageDto.setName(storage.getName());

        if (storage.getProductConnectionList() != null) {
            List<ProductDto> productDtoList = storage.getProductConnectionList().stream()
                    .map(ProductConnection::getProductId)
                    .map(product -> toProductDto(product, storageId))
                    .toList();
            storageDto.setProductList(productDtoList);
        }
        return storageDto;
    }
}
