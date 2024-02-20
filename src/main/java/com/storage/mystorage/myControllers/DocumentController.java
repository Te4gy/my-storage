package com.storage.mystorage.myControllers;

import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.myDto.wrapperDto.TransferWrapper;
import com.storage.mystorage.myEntitys.Product;
import com.storage.mystorage.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/document")
public class DocumentController {

    final DocumentService documentService;

    @PostMapping("/admission")
    public ResponseEntity<StorageDto> admission(@RequestBody DocumentsWrapper documentsWrapper) {
        StorageDto boughtProducts = documentService.admission(documentsWrapper);
        return new ResponseEntity<>(boughtProducts, HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<StorageDto> sell(@RequestBody DocumentsWrapper sellWrapper){
        StorageDto soldProductsList = documentService.sell(sellWrapper);
        return new ResponseEntity<>(soldProductsList, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<List<StorageDto>> transfer(@RequestBody TransferWrapper transferWrapper){
        Long fromStorageId = transferWrapper.getStorageFromId();
        Long toStorageId = transferWrapper.getStorageToId();
        List<Product> productList = transferWrapper.getProductList();
        List<StorageDto> response = documentService.transfer(fromStorageId, toStorageId, productList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
