package com.storage.mystorage.myDto.wrapperDto;

import com.storage.mystorage.myEntitys.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransferWrapper {

    int documentNumber;
    Long storageFromId;
    Long StorageToId;
    List<Product> productList;
}
