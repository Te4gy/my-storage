package com.storage.mystorage.services;

import com.storage.mystorage.myEntitys.ProductConnection;
import com.storage.mystorage.services.EntityRepos.ProductConnectionService;
import com.storage.mystorage.services.EntityRepos.ProductService;
import com.storage.mystorage.services.EntityRepos.StorageService;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.myEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    final StorageService storageService;
    final ProductService productService;
    final ProductConnectionService productConnectionService;

    public List<StorageDto> admission(DocumentsWrapper documentsWrapper) {
        Long storageId = documentsWrapper.getStorageId();

        Product product = documentsWrapper.getProduct();
        int amount = documentsWrapper.getAmount();

        productService.saveProduct(product);
        ProductConnection productConnection =
                productConnectionService.saveProductToStorageConnection(storageId, product, amount);
        Storage storage = productConnection.getStorage();
        StorageDto storageDto = StorageProductConvertor.toStorageDto(storage);
        return List.of(storageDto);
//        return new ArrayList<>();
    }

    public List<StorageDto> sell(DocumentsWrapper sellWrapper) {
        List<Product> productListToSell = sellWrapper.getProductList();
        Long storageId = sellWrapper.getStorageId();
        Storage storage = storageService.findStorageById(storageId);
        List<Product> soldProducts = removeProductsFromStorage(storage, productListToSell);
        return List.of(storageService.saveProductsToStorage(storageId, soldProducts));
    }

    public List<StorageDto> transfer(DocumentsWrapper documentsWrapper) {
//        Long fromStorageId = documentsWrapper.getStorageFromId();
//        Long toStorageId = documentsWrapper.getStorageToId();
//        List<Product> productToTransferList = documentsWrapper.getProductList();
//
//        //todo ! постарался снизить до минимума количество обращений к бд
//        List<Storage> allStorages = storageService.findAllStorages();
//        //todo ? может есть вариант получше?
//        Map<Long, Storage> storageMap = allStorages.stream()
//                .filter(storage -> storage.getId().equals(fromStorageId) || storage.getId().equals(toStorageId))
//                .collect(Collectors.toMap(Storage::getId, Function.identity()));
//
//        Storage storageFrom = Optional.ofNullable(storageMap.get(fromStorageId)).orElseThrow(() -> new RuntimeException("Storage from not found"));
//        Storage storageTo = storageMap.get(toStorageId);
//        //todo ? это один из вариантов, и мой вопрос какой лучше тот что выше или тот что ниже?
////        List<Storage> fromToStorages = allStorages.stream()
////                .filter(storage -> storage.getId().equals(fromStorageId) || storage.getId().equals(toStorageId))
////                .toList();
////        Storage storageTo = fromToStorages.stream()
////                .filter(storage -> storage.getId().equals(fromStorageId))
////                .findFirst()
////                .orElseThrow(RuntimeException::new);
////        Storage storageFrom = fromToStorages.stream()
////                .filter(storage -> storage.getId().equals(toStorageId))
////                .findFirst()
////                .orElseThrow(RuntimeException::new);
//
//        List<Product> fromStorageProducts = extractProductsFromStorage(storageFrom, productToTransferList);
//
//        storageTo.setProductList(productToTransferList); //добавили продукты на складTO
//
//        StorageDto updatedStorageToDto = storageService.saveProductsToStorage(toStorageId, fromStorageProducts); //возвращает дто обновленного складаТО
//
//        storageFrom.getProductList().removeAll(fromStorageProducts);
//        Storage updatedStorageFrom = storageService.saveStorage(storageFrom);
//        StorageDto updatedStorageFromDto = StorageProductConvertor.toStorageDto(updatedStorageFrom);
//
//        return List.of(updatedStorageToDto, updatedStorageFromDto);
        return null;
    }


    public List<Product> removeProductsFromStorage(Storage storage, List<Product> productsToRemove) {
//        List<Product> productsInStorage = storage.getProductList();
//        List<Product> soldProducts = new ArrayList<>();
//        //todo думаю можно заменить на стрим
//        for (Product productToSell : productsToRemove) {
//            for (Product productInStorage : productsInStorage) {
//                if (productToSell.getId().equals(productInStorage.getId())) {
//                    int lastBuyPrice = productToSell.getLastBuyPrice();
//                    productInStorage.setLastBuyPrice(lastBuyPrice);
//                    productInStorage.setExists(false);
//                    soldProducts.add(productInStorage);
//                }
//            }
//        }
//        return soldProducts;
        return null;
    }


    public List<Product> extractProductsFromStorage(Storage storage, List<Product> productList) {
//        List<Product> productListFromStorage = storage.getProductList();
//        List<Product> extractedList = new ArrayList<>();
//        for (Product productFromStorage : productListFromStorage) {
//            for (Product productToExtract : productList) {
//                if (productFromStorage.getId().equals(productToExtract.getId())) {
//                    extractedList.add(productFromStorage);
//                }
//            }
//        }
//        return extractedList;
        return null;
    }
}
