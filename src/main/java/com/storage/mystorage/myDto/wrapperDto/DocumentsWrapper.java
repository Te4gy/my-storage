package com.storage.mystorage.myDto.wrapperDto;

import com.storage.mystorage.myEntitys.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DocumentsWrapper {
    private Long storageId;
    private int documentNumber;
    private List<Product> productList;
}
