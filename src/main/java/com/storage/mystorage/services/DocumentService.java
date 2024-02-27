package com.storage.mystorage.services;

import com.storage.mystorage.entities.Product;
import com.storage.mystorage.entities.ProductConnection;
import com.storage.mystorage.entities.Storage;
import com.storage.mystorage.services.crud.ProductConnectionService;
import com.storage.mystorage.services.crud.ProductService;
import com.storage.mystorage.services.crud.StorageService;
import com.storage.mystorage.utils.dto.answersDto.StorageDto;
import com.storage.mystorage.utils.dto.wrapperDto.DocumentsWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    final StorageService storageService;
    final ProductService productService;
    final ProductConnectionService productConnectionService;
    private final StorageProductConverter converter;


    @Transactional
    public List<StorageDto> admission(DocumentsWrapper documentsWrapper) {
        Product product = documentsWrapper.getProduct();

        product = !productService.existsByName(product.getName())
                ? productService.save(product)
                : productService.getByName(product.getName());

        Long storageId = documentsWrapper.getStorageId();
        Storage storage = storageService.findStorageById(storageId);

        int amount = documentsWrapper.getAmount();

        if (productConnectionService.existsByProductIdAndStorageId(product.getId(), storageId)) {
            ProductConnection byProductId =
                    productConnectionService.findByProductIdAndStorageId(product.getId(), storageId);
            int currentAmount = byProductId.getAmount() + amount;
            byProductId.setAmount(currentAmount);
            productConnectionService.save(byProductId);
        } else {
            ProductConnection productConnection = new ProductConnection();
            productConnection.setProductId(product.getId());
            productConnection.setStorageId(storage.getId());
            productConnection.setAmount(amount);
            List<ProductConnection> productConnectionList = product.getProductConnectionList();
            productConnectionList.add(productConnection);
            product.setProductConnectionList(productConnectionList);
            productService.save(product);
        }

        //todo я б не советовала возвращать такой объект. лучше вернуть сколько добавили элементов или сумма в итоге
        storage = storageService.findStorageById(storageId);
        StorageDto storageDto = converter.toStorageDto(storage);
        return Collections.singletonList(storageDto);
    }

    @Transactional
    public List<StorageDto> sell(DocumentsWrapper sellWrapper) {
        //        boolean exists = productConnectionService.exists("chair", 5);
        //        productConnectionService.delete("chair", 5);

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
