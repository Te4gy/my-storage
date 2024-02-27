package com.storage.mystorage.utils.dto.answersDto;


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
    private int lastSellPrice;
    private int lastBuyPrice;
    private boolean isExists;
    private int amount;
}
