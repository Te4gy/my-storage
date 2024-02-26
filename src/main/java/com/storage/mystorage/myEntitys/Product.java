package com.storage.mystorage.myEntitys;

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

    private String name;

    private int lastSellPrice;

    private int lastBuyPrice;

    private boolean isExists = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductConnection> productConnectionList;


    public void addProductConnection(ProductConnection productConnection){
        if (productConnection != null) {
            if (this.productConnectionList == null) {
                this.productConnectionList = new ArrayList<>();
            }
            this.productConnectionList.add(productConnection);
            productConnection.setProduct(this);
        }

    }
}
