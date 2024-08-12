package com.storage.mystorage.utils.myDto.answersDto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductConnectionDto {
    private Long id;
    private ProductDto product;
    private StorageDto storage;
}
