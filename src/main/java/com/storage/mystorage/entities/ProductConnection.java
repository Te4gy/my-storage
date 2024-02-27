package com.storage.mystorage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_product_storage", columnNames = {"product_id", "storage_id"}))
public class ProductConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long productId;

    Long storageId;

    int amount;

}
