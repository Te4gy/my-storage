package com.storage.mystorage.services.EntityRepos;

import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.ProductConnection;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.myRepositories.ProductConnectionRepository;
import com.storage.mystorage.myRepositories.ProductRepository;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductConnectionService {

    final ProductConnectionRepository productConnectionRepository;
    final ProductService productService;
    final StorageService storageService;

    @Transactional
    public ProductConnection saveProductToStorageConnection(Storage storage, Product product, int amount) {
//        // Получаем управляемый экземпляр product из базы данных
//        Product managedProduct = productService.findProductById(product.getId());
//        // Получаем управляемый экземпляр storage из базы данных
//        Storage managedStorage = storageService.findStorageById(storage.getId());


        ProductConnection productConnection = new ProductConnection();
        if (isProductConnectionExists(storage, product)) {
            productConnection = getProductConnection(storage, product);

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

//    public ProductConnection changeAmountInStorage(Storage storage, Product product, int amountToTransfer){
//
//        ProductConnection productConnectionInStorageTo = storage.getProductConnectionList().stream()
//                .filter(e-> e.getProduct().getId().equals(product.getId()))
//                .findFirst()
//                .orElseThrow();
//        int newAmountInStorageTo = productConnectionInStorageTo.getAmount()+amountToTransfer;
//        ProductConnection savedProductConnectionTo =
//                        saveProductToStorageConnection(
//                        storage,
//                        product,
//                        newAmountInStorageTo
//                );
//        return savedProductConnectionTo;
//    }
}
