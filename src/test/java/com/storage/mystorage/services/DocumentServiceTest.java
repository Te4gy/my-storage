package com.storage.mystorage.services;

import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.ProductConnection;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.services.EntityRepos.ProductConnectionService;
import com.storage.mystorage.services.EntityRepos.ProductService;
import com.storage.mystorage.services.EntityRepos.StorageService;
import com.storage.mystorage.utils.myDto.answersDto.ProductDto;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {



    @Mock
    StorageService storageService;
    @Mock
    ProductConnectionService productConnectionService;

    ProductService productService;

    @Test
    //Тест проверяется метод Sell в классе DocumentsService. На складе 2 стула и мы пытаемся продать один
    // и в результате должен остаться 1 стул
    void sellTest(){


        DocumentService documentService = new DocumentService(storageService, productService, productConnectionService);

        DocumentsWrapper sellWrapper = new DocumentsWrapper();
        Product product = new Product();
        Product productInStorage = new Product();
        Storage storageFromDb = new Storage();
        ProductConnection productConnectionInDB = new ProductConnection();
        List<ProductConnection> productConnectionFromDbList = new ArrayList<>();

        product.setId(1L);
        product.setSellPrice(100);

        productInStorage.setId(1L);


        storageFromDb.setId(1L);
        storageFromDb.setName("Red");


        productConnectionInDB.setProduct(productInStorage);
        productConnectionInDB.setAmount(2);
        productConnectionInDB.setStorage(storageFromDb);

        productInStorage.setProductConnectionList(List.of(productConnectionInDB));

        productConnectionFromDbList.add(productConnectionInDB);

        storageFromDb.setProductConnectionList(productConnectionFromDbList);

        Storage storage = new Storage();
        storage.setId(1L);
        storage.setName("Red");

        ProductConnection resultProductConnection = new ProductConnection();
        resultProductConnection.setAmount(1);
        product.setProductConnectionList(List.of(resultProductConnection));
        resultProductConnection.setProduct(product);
        storage.setProductConnectionList(List.of(resultProductConnection));
        resultProductConnection.setStorage(storage);


        sellWrapper.setStorageId(1L);
        sellWrapper.setProduct(product);
        sellWrapper.setAmount(1);

        Mockito.when(storageService.findStorageById(1L)).thenReturn(storageFromDb);

        int newAmount = 1;
        Mockito.when(productConnectionService.saveProductToStorageConnection(
                storageFromDb,
                productInStorage,
                newAmount)).thenReturn(resultProductConnection);


        List<StorageDto> actualAns = documentService.sell(sellWrapper);


        StorageDto expectedAns = new StorageDto();
        expectedAns.setId(1L);
        expectedAns.setName("Red");

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setSellPrice(100);
        productDto.setAmount(1);

        expectedAns.setProductList(List.of(productDto));

        Assertions.assertEquals(List.of(expectedAns), actualAns);

    }

    @Test
    void transferTest(){

        DocumentService documentService = new DocumentService(storageService, productService, productConnectionService);

        ProductConnection productConnectionFromStorage = new ProductConnection();
        productConnectionFromStorage.setAmount(1);

        Product product = new Product();
        product.setId(1L);
        product.setProductConnectionList(List.of(productConnectionFromStorage));
        productConnectionFromStorage.setProduct(product);


        DocumentsWrapper documentsWrapper = new DocumentsWrapper();
        documentsWrapper.setProduct(product);
        documentsWrapper.setAmount(1);
        documentsWrapper.setStorageFromId(1L);
        documentsWrapper.setStorageToId(2L);

        Storage storageFrom = new Storage();
        storageFrom.setId(1L);
        storageFrom.setProductConnectionList(List.of(productConnectionFromStorage));
        Storage storageTo = new Storage();
        storageTo.setId(2L);
        storageTo.setProductConnectionList(List.of());


        Mockito.when(storageService.findStorageById(1L)).thenReturn(storageFrom);
        Mockito.when(storageService.findStorageById(2L)).thenReturn(storageTo);


        ProductConnection newProductConnectionFromStorage = new ProductConnection();
        newProductConnectionFromStorage.setAmount(0);
        newProductConnectionFromStorage.setProduct(product);
        storageFrom.setProductConnectionList(List.of(newProductConnectionFromStorage));

        Mockito.when(productConnectionService.saveProductToStorageConnection(storageFrom, product,0))
                .thenReturn(newProductConnectionFromStorage);

        ProductConnection newProductConnectionToStorage = new ProductConnection();
        newProductConnectionToStorage.setAmount(1);
        newProductConnectionToStorage.setProduct(product);

        Mockito.when(productConnectionService.saveProductToStorageConnection(storageTo, product, 1))
                .thenReturn(newProductConnectionToStorage);





        List<StorageDto> ansStorageDtoList = documentService.transfer(documentsWrapper);
        int actualAmountFromStorage = ansStorageDtoList.stream()
                .filter(e -> e.getId().equals(1L))
                .findFirst()
                .orElseGet(StorageDto::new)
                .getProductList()
                .get(1)
                .getAmount();

        int actualAmountToStorage = ansStorageDtoList.stream()
                .filter(e -> e.getId().equals(2L))
                .findFirst()
                .orElseGet(StorageDto::new)
                .getProductList()
                .get(1)
                .getAmount();


        int expectedAmountFromStorage = 0;
        int expectedAmountToStorage = 0;

        Assertions.assertEquals(expectedAmountFromStorage, actualAmountFromStorage);
        Assertions.assertEquals(expectedAmountToStorage, actualAmountToStorage);

    }













//
//    @Mock
//    StorageService storageService;
//
//    @Test
//    void sellTest() {
//
//        DocumentService documentService = new DocumentService(storageService);
//
//        Long storageId = 1L;
//        Product product1 = new Product();
//        Product product2 = new Product();
//        product1.setId(1L);
//        product2.setId(2L);
//        Storage storage = new Storage();
//        storage.setId(1L);
//        List<Product> productList = List.of(product1, product2);
//
//        DocumentsWrapper documentsWrapper = new DocumentsWrapper();
//        documentsWrapper.setProductList(productList);
//        documentsWrapper.setStorageId(storageId);
//
//        List<Product> soldProducts = new ArrayList<>();
//        soldProducts.add(product1);
//        soldProducts.add(product2);
//
//        storage.setProductList(productList);
//        StorageDto storageDto = StorageProductConvertor.toStorageDto(storage);
//
//        Mockito.when(storageService.saveProductsToStorage(storageId, soldProducts)).thenReturn(storageDto);
//
//        var ansToCheck = documentService.admission(documentsWrapper);
//
//        var expectedAns = List.of(storageDto);
//
//        Assertions.assertEquals(expectedAns, ansToCheck);
//
//    }
}
