package com.storage.mystorage.services.entityServices;

import com.storage.mystorage.allEntitys.Product;
import com.storage.mystorage.allEntitys.ProductConnection;
import com.storage.mystorage.allEntitys.Storage;
import com.storage.mystorage.allRepositories.entitysRepos.ProductConnectionRepository;
import com.storage.mystorage.allRepositories.RedisRepository;
import com.storage.mystorage.utils.myDto.answersDto.ProductConnectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductConnectionService {

    final ProductConnectionRepository productConnectionRepository;
    final ProductService productService;
    final StorageService storageService;
    final RedisRepository redisRepository;



    @Transactional
    public ProductConnection saveProductToStorageConnection(Storage storage, Product product, int amount) {
//        // Получаем управляемый экземпляр product из базы данных
//        Product managedProduct = productService.findProductById(product.getId());
//        // Получаем управляемый экземпляр storage из базы данных
//        Storage managedStorage = storageService.findStorageById(storage.getId());


        ProductConnection productConnection = new ProductConnection();
        if (isProductConnectionExists(storage, product)) {
            productConnection = getProductConnection(storage, product);

//        } else if (isProductExistsInStorage(storage, product)) {
//            Product productFromDb = productService.findProductById(product.getId());
//            storage.setProductConnectionList(List.of(productConnection));
//            productConnection.setProduct(productFromDb);
//            product.addProductConnection(productConnection);
//            productConnection.setStorage(storage);

        } else if (isProductExistsInStorage(storage, product)) {
            Product productFromDb = productService.findProductById(product.getId());
            storage.setProductConnectionList(List.of(productConnection));
            productConnection.setProduct(productFromDb);
            product.addProductConnection(productConnection);
            productConnection.setStorage(storage);


        } else {

////            storage.setProductConnectionList(List.of(productConnection));
//            productConnection.setProduct(managedProduct);
//            productConnection.setProduct(product);
//            managedProduct.addProductConnection(productConnection);
//            productConnection.setStorage(managedStorage);
////            storage.setProductConnectionList(List.of(productConnection));
//            List<ProductConnection> connections = new ArrayList<>(managedStorage.getProductConnectionList());
//            connections.add(productConnection);
//            managedStorage.setProductConnectionList(connections);

            productConnection.setProduct(product);
            product.addProductConnection(productConnection);
            productConnection.setStorage(storage);
//            storage.setProductConnectionList(List.of(productConnection));
            List<ProductConnection> connections = new ArrayList<>(storage.getProductConnectionList());
            connections.add(productConnection);
            storage.setProductConnectionList(connections);

        }
        productConnection.setAmount(amount);
        return productConnectionRepository.save(productConnection);
    }


    ProductConnection getProductConnection(Storage storage, Product product) {
        Long productId = product.getId();
        Long storageId = storage.getId();

        Long productConnectionId = storage.getProductConnectionList().stream()
                .filter(e -> e.getProduct().getId().equals(productId))
                .filter(e -> e.getStorage().getId().equals(storageId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException
                        ("There is no connection between " + storage.getName() + "and " + product.getName()))
                .getId();
        return findProductConnectionById(productConnectionId);

    }

    boolean isProductExistsInStorage(Storage storage, Product product) {
        Long productId = product.getId();
        return storage.getProductConnectionList().stream()
                .anyMatch(e -> e.getProduct().getId().equals(productId));

    }


    public boolean isProductConnectionExists(Storage storage, Product product) {
        Long productId = product.getId();
        Long storageId = storage.getId();
        return storage.getProductConnectionList().stream()
                .filter(e -> e.getProduct().getId().equals(productId))
                .anyMatch(e -> e.getStorage().getId().equals(storageId));

    }

    public ProductConnection findProductConnectionById(Long id) {
        return productConnectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("productConnection"));
    }

    public ProductConnection changeAmountInStorage(Storage storage, Product product, int amountToTransfer){

        ProductConnection productConnectionInStorageTo = storage.getProductConnectionList().stream()
                .filter(e-> e.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseThrow();
        int newAmountInStorageTo = productConnectionInStorageTo.getAmount()+amountToTransfer;
        ProductConnection savedProductConnectionTo =
                        saveProductToStorageConnection(
                        storage,
                        product,
                        newAmountInStorageTo
                );
        return savedProductConnectionTo;
    }

    public ProductConnection saveProductConnection(ProductConnection productConnection){
        return productConnectionRepository.save(productConnection);
    }

}
