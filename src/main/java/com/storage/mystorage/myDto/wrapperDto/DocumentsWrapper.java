package com.storage.mystorage.myDto.wrapperDto;

import com.storage.mystorage.myEntitys.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

//todo ? Вроде как места больше тратим создавая лонг переменные которые не используем, но за о вся информация
//в одном объекте. Взял пример с месседжей в телеграм боте, там много очень слотов для инфы но используются не все
public class DocumentsWrapper {
    private Long storageId;
    private Long storageFromId;
    private Long storageToId;
    private int documentNumber;
    private List<Product> productList;
}
