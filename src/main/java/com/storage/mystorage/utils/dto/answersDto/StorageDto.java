package com.storage.mystorage.utils.dto.answersDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StorageDto {
    private Long id;
    private String name;
    private List<ProductDto> productList;
}
