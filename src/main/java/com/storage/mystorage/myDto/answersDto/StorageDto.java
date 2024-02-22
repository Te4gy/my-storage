package com.storage.mystorage.myDto.answersDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StorageDto {
    private Long id;
    private String name;
    private List<ProductDto> productList;
}
