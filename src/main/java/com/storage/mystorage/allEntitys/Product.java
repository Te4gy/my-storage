package com.storage.mystorage.allEntitys;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int sellPrice;

    private int purchasePrice;

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
