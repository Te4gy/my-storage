package com.storage.mystorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private int lastSellPrice;

    private int lastBuyPrice;

    @Transient //todo просто демонстрирую аннотацию для тебя. ее не будет в бд
    public boolean isExists() {
        return productConnectionList.stream()
                .mapToInt(ProductConnection::getAmount)
                .sum() != 0;
    }

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<ProductConnection> productConnectionList = new ArrayList<>();

}
