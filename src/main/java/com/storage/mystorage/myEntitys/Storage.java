package com.storage.mystorage.myEntitys;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private List<ProductConnection> productConnectionList;

}
