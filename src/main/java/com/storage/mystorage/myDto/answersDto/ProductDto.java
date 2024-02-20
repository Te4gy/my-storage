package com.storage.mystorage.myDto.answersDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private int lastSellPrice;
    private int lastBuyPrice;
    private boolean isExists;
}
