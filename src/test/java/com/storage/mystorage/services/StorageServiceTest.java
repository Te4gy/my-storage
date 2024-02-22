package com.storage.mystorage.services;

import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.myRepositories.StorageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {

    @Mock
    StorageRepository storageRepository;

    @Test
    void saveProductsToStorageTest() {
        StorageService storageService = new StorageService(storageRepository);
        Long storageId = 1L;
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(1L);
        product2.setId(2L);
        Storage storage = new Storage();
        storage.setId(1L);
        Mockito.when(storageRepository.findById(storageId)).thenReturn(Optional.of(storage));
        List<Product> productList = List.of(product1, product2);
        storage.setProductList(productList);
        Mockito.when(storageRepository.save(storage)).thenReturn(storage);
        var ansToCheck = storageService.saveProductsToStorage(storageId, productList);
        ansToCheck.setId(1L);

        var expectedAns = StorageProductConvertor.toStorageDto(storage);
        expectedAns.setId(1L);

        Assertions.assertEquals(expectedAns.getProductList(), ansToCheck.getProductList());


    }
}
