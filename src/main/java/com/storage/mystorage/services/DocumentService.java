package com.storage.mystorage.services;

import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    final StorageService storageService;

    //todo Много запросов в БД!
    public List<StorageDto> admission(DocumentsWrapper documentsWrapper) {
        var productList = documentsWrapper.getProductList();
        Long storageId = documentsWrapper.getStorageId();
        return List.of(storageService.saveProductsToStorage(storageId, productList));
    }

    //todo Что если продукт уже отсутствует?
    //todo Много запросов в БД!
    public List<StorageDto> sell(DocumentsWrapper sellWrapper) {
        List<Product> productListToSell = sellWrapper.getProductList();
        Long storageId = sellWrapper.getStorageId();
        List<Product> soldProducts = removeProductsFromStorage(storageId, productListToSell);
        return List.of(storageService.saveProductsToStorage(storageId, soldProducts));
    }


    //todo При переносе товара, товар должен УДАЛЯТЬСЯ с прошлого склада, либо оставить пока так а потом доработать.
    public List<StorageDto> transfer(DocumentsWrapper documentsWrapper) {
        Long fromStorageId = documentsWrapper.getStorageFromId();
        Long toStorageId = documentsWrapper.getStorageToId();
        List<Product> productToTransferList = documentsWrapper.getProductList();

        Storage storageTo = storageService.findStorageById(toStorageId);

        Storage storageFrom = storageService.findStorageById(fromStorageId);

        //todo мы дважды обращаемся к бд за одним и тем же запросом, можно обратиться в начале метода и потом использовать
        List<Product> fromStorageProducts = extractProductsFromStorage(fromStorageId, productToTransferList);

        storageTo.setProductList(productToTransferList); //добавили продукты на складTO

        StorageDto updatedStorageToDto = storageService.saveProductsToStorage(toStorageId, fromStorageProducts); //возвращает дто обновленного складаТО

        //todo мы дважды обращаемся к бд за одним и тем же запросом, можно обратиться в начале метода и потом использовать
//        List<Product> soldProducts = extractProductsFromStorage(fromStorageId, fromStorageProducts); //удаляем товары с складаFROM

        storageFrom.getProductList().removeAll(fromStorageProducts);
        Storage updatedStorageFrom = storageService.saveStorage(storageFrom);
        StorageDto updatedStorageFromDto = StorageProductConvertor.toStorageDto(updatedStorageFrom);

        return List.of(updatedStorageToDto, updatedStorageFromDto);
    }


    public List<Product> removeProductsFromStorage(Long storageId, List<Product> productsToRemove) {
        List<Product> productsInStorage = storageService.findStorageById(storageId).getProductList();
        List<Product> soldProducts = new ArrayList<>();
        for (Product productToSell : productsToRemove) {
            //todo можно заменить на стрим
            for (Product productInStorage : productsInStorage) {
                if (productToSell.getId().equals(productInStorage.getId())) {
                    int lastBuyPrice = productToSell.getLastBuyPrice();
                    productInStorage.setLastBuyPrice(lastBuyPrice);
                    productInStorage.setExists(false);
                    soldProducts.add(productInStorage);
                }
            }
        }
        return soldProducts;
    }


    public List<Product> extractProductsFromStorage(Long storageId, List<Product> productList) {
        List<Product> productListFromStorage = storageService.findStorageById(storageId).getProductList();
        List<Product> extractedList = new ArrayList<>();
        for (Product productFromStorage : productListFromStorage) {
            for (Product productToExtract : productList) {
                if (productFromStorage.getId().equals(productToExtract.getId())) {
                    extractedList.add(productFromStorage);
                }
            }
        }
        return extractedList;
    }
}
