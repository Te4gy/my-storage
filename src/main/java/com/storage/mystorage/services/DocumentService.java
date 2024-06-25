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
import org.springframework.transaction.annotation.Transactional;

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
        Storage storage = storageService.findStorageById(storageId);

        Product product = documentsWrapper.getProduct();
        int amount = documentsWrapper.getAmount();

        productService.saveProduct(product);
        productConnectionService.saveProductToStorageConnection(storage, product, amount);
        StorageDto storageDto = StorageProductConvertor.toStorageDto(storage);
        return List.of(storageDto);
    }

    public List<StorageDto> sell(DocumentsWrapper sellWrapper) {
        Product productToSell = sellWrapper.getProduct();
        Long productId = productToSell.getId();
        Long storageId = sellWrapper.getStorageId();
        int amountToSell = sellWrapper.getAmount();

        Storage storage = storageService.findStorageById(storageId);

        List <ProductConnection> productConnectionList = storage.getProductConnectionList();
        ProductConnection productConnection = productConnectionList.stream()
                .filter(e -> e.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such product!"));

        Product product = productConnection.getProduct();
        product.setSellPrice(sellWrapper.getProduct().getSellPrice());
        int newAmount = productConnection.getAmount()-amountToSell;

        ProductConnection savedProductConnection = productConnectionService
                .saveProductToStorageConnection(
                        storage,
                        product,
                        newAmount);
        return List.of(StorageProductConvertor.toStorageDto(savedProductConnection.getStorage()));
    }
    @Transactional
    public List<StorageDto> transfer(DocumentsWrapper documentsWrapper) {

        Long storageFromId = documentsWrapper.getStorageFromId();
        Long storageToId = documentsWrapper.getStorageToId();
        Long productId = documentsWrapper.getProduct().getId();

        Storage storageFrom = storageService.findStorageById(storageFromId);
        Storage storageTo = storageService.findStorageById(storageToId);

        Product productToTransfer = productService.findProductById(productId);

        Long productToTransferId = productToTransfer.getId();

        int amountToTransfer = documentsWrapper.getAmount();

        int productConnectionFromNewAmount =
                storageFrom.getProductConnectionList().stream()
                        .filter(e -> e.getProduct().getId().equals(productToTransferId))
                        .findFirst()
                        .orElseThrow()
                        .getAmount()
                - amountToTransfer;


        ProductConnection savedProductConnectionFrom =
                productConnectionService.saveProductToStorageConnection(
                        storageFrom,
                        productToTransfer,
                        productConnectionFromNewAmount
                );


        boolean isProductExistsInStorageTo =
                productConnectionService.isProductConnectionExists(storageTo, productToTransfer);


        ProductConnection savedProductConnectionTo;

        if (isProductExistsInStorageTo){
            ProductConnection productConnectionInStorageTo = storageTo.getProductConnectionList().stream()
                    .filter(e-> e.getProduct().getId().equals(productToTransferId))
                    .findFirst()
                    .orElseThrow();
            int newAmountInStorageTo = productConnectionInStorageTo.getAmount()+amountToTransfer;
            savedProductConnectionTo =
                    productConnectionService.saveProductToStorageConnection(
                            storageTo,
                            productToTransfer,
                            newAmountInStorageTo
                    );
//            savedProductConnectionTo = productConnectionService.changeAmountInStorage(storageTo, productToTransfer, amountToTransfer);
        }

        else {
            savedProductConnectionTo =
                    productConnectionService.saveProductToStorageConnection(
                            storageTo,
                            productToTransfer,
                            amountToTransfer
                    );
        }

        return List.of(StorageProductConvertor.toStorageDto(savedProductConnectionFrom.getStorage()),
                StorageProductConvertor.toStorageDto(savedProductConnectionTo.getStorage()));

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
