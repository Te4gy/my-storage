package com.storage.mystorage.utils.myDto.answersDto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductDto {
    private Long id;
    private String name;
    private int sellPrice;
    private int purchasePrice;
    private int amount;
}
