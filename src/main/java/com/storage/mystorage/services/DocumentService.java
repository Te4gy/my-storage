package com.storage.mystorage.services;

import com.storage.mystorage.allEntitys.ProductConnection;
import com.storage.mystorage.services.entityServices.ProductConnectionService;
import com.storage.mystorage.services.entityServices.ProductService;
import com.storage.mystorage.services.entityServices.StorageService;
import com.storage.mystorage.services.tools.StorageProductConvertor;
import com.storage.mystorage.utils.myDto.answersDto.ProductConnectionDto;
import com.storage.mystorage.utils.myDto.answersDto.ProductDto;
import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.allEntitys.Product;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.allEntitys.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    final StorageService storageService;
    final ProductService productService;
    final ProductConnectionService productConnectionService;
    final RedisService redisService;

    @Transactional
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

    @Transactional
    public List<StorageDto> sell(DocumentsWrapper sellWrapper) {
        Product productToSell = sellWrapper.getProduct();
        Long productId = productToSell.getId();
        Long storageId = sellWrapper.getStorageId();
        int amountToSell = sellWrapper.getAmount();

// 1  /\


        List<ProductConnectionDto> cachedProductConnectionDtoList =
                redisService.findAllProductConnectionDtoFromCache();

        Optional<ProductConnectionDto> optionalProductConnectionDto = cachedProductConnectionDtoList.stream()
                .filter(e -> e.getProduct().getId().equals(productId))
                .findFirst();

        ProductConnection productConnection;

        if (optionalProductConnectionDto.isPresent()){
            ProductConnectionDto cachedProductConnectionDto = optionalProductConnectionDto.get();

            ProductDto productDto = cachedProductConnectionDto.getProduct();
            Product product = new Product();
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setPurchasePrice(productDto.getPurchasePrice());
            product.setSellPrice(productDto.getSellPrice());

            int amount =productDto.getAmount();

            StorageDto storageDto = cachedProductConnectionDto.getStorage();
            Storage storage = new Storage();
            storage.setId(storageDto.getId());
            storage.setName(storageDto.getName());

            productConnection = new ProductConnection(
                    cachedProductConnectionDto.getId(),
                    product,
                    storage,
                    amount
                );

//            productConnectionService.saveProductConnection(productConnection);
//
//
//            return List.of();

        }

        else {
            Storage storage = storageService.findStorageById(storageId);

            List <ProductConnection> productConnectionList = storage.getProductConnectionList();
            productConnection = productConnectionList.stream()
                    .filter(e -> e.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No such product!"));
        }

        Product product = productConnection.getProduct();
        Storage storage = productConnection.getStorage();
        product.setSellPrice(sellWrapper.getProduct().getSellPrice());
        int newAmount = productConnection.getAmount()-amountToSell;
        productConnection.setProduct(product);
        productConnection.setStorage(storage);
        productConnection.setAmount(newAmount);


//        return List.of();
        ProductConnection savedProductConnection = productConnectionService
                .saveProductConnection(productConnection);

        return List.of(StorageProductConvertor.fromProductConnectionTotoStorageDtoAnswer(savedProductConnection));



//  1   \/
//        List <ProductConnection> productConnectionList = storage.getProductConnectionList();
//        ProductConnection productConnection = productConnectionList.stream()
//                .filter(e -> e.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No such product!"));

//        Product product = productConnection.getProduct();
//        product.setSellPrice(sellWrapper.getProduct().getSellPrice());
//        int newAmount = productConnection.getAmount()-amountToSell;
//
//        ProductConnection savedProductConnection = productConnectionService
//                .saveProductToStorageConnection(
//                        storage,
//                        product,
//                        newAmount);
//        return List.of(StorageProductConvertor.toStorageDto(savedProductConnection.getStorage()));
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

        int amountToTransfer = documentsWrapper .getAmount();

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
            savedProductConnectionTo = productConnectionService.changeAmountInStorage(
                    storageTo,
                    productToTransfer,
                    amountToTransfer);
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
