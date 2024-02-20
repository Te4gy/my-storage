package com.storage.mystorage.myControllers;

import com.storage.mystorage.myDto.answersDto.ProductDto;
import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.myEntitys.Storage;
import com.storage.mystorage.services.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportsController {

    final ReportsService reportsService;

    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductDto>> allProducts(){
        List<ProductDto> allProducts = reportsService.allProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/productsInStock")
    public ResponseEntity<List<StorageDto>> productsInStock(){
        List<StorageDto> productsInStock = reportsService.productsInStock();
        return new ResponseEntity<>(productsInStock, HttpStatus.OK);
    }
}
