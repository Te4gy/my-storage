package com.storage.mystorage.entities;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void isExistsFalse() {
        var product = new Product();
        assertFalse(product.isExists());
    }

    @Test
    void isExistsFalse2() {
        var product = new Product();
        ProductConnection productConnection = new ProductConnection();
        product.setProductConnectionList(Collections.singletonList(productConnection));
        assertFalse(product.isExists());
    }

    @Test
    void isExistsFalse3() {
        var product = new Product();
        ProductConnection productConnection = new ProductConnection();
        productConnection.setAmount(0);
        product.setProductConnectionList(Collections.singletonList(productConnection));
        assertFalse(product.isExists());
    }

    @Test
    void isExistsTrue() {
        var product = new Product();
        ProductConnection productConnection = new ProductConnection();
        productConnection.setAmount(1);
        product.setProductConnectionList(Collections.singletonList(productConnection));
        assertTrue(product.isExists());
    }
}