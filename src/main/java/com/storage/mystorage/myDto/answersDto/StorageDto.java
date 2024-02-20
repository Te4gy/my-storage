package com.storage.mystorage.myDto.answersDto;

import com.storage.mystorage.myEntitys.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StorageDto {
    private Long id;
    private String name;
    private List<ProductDto> productList;
}
