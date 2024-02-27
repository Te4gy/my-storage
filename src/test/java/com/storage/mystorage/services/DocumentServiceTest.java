package com.storage.mystorage.services;

import com.storage.mystorage.entities.Product;
import com.storage.mystorage.entities.Storage;
import com.storage.mystorage.services.crud.StorageService;
import com.storage.mystorage.utils.dto.wrapperDto.DocumentsWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    StorageService storageService;

    @Test
    void sellTest() {

        DocumentService documentService = new DocumentService(storageService, null, null, null);

        Long storageId = 1L;
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(1L);
        product2.setId(2L);
        Storage storage = new Storage();
        storage.setId(1L);
        List<Product> productList = List.of(product1, product2);

        DocumentsWrapper documentsWrapper = new DocumentsWrapper();
        documentsWrapper.setProductList(productList);
        documentsWrapper.setStorageId(storageId);

        List<Product> soldProducts = new ArrayList<>();
        soldProducts.add(product1);
        soldProducts.add(product2);

        //todo надо поправить
//        storage.setProductList(productList);
//        StorageProductConverter storageProductConverter = new StorageProductConverter();
//        StorageDto storageDto = storageProductConverter.toStorageDto(storage);
//
//        Mockito.when(storageService.saveProductsToStorage(storageId, soldProducts)).thenReturn(storageDto);
//
//        var ansToCheck = documentService.admission(documentsWrapper);
//
//        var expectedAns = List.of(storageDto);
//
//        Assertions.assertEquals(expectedAns, ansToCheck);

    }
}
