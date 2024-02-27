package com.storage.mystorage.controllers;

import com.storage.mystorage.utils.dto.answersDto.StorageDto;
import com.storage.mystorage.utils.dto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.services.DocumentService;
import com.storage.mystorage.services.Receiver;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/document")
public class DocumentController {

    private final Receiver receiver;

    @PostMapping("/{document}")
    public ResponseEntity<List<StorageDto>> document(@RequestBody DocumentsWrapper documentsWrapper,
                                                     @PathVariable("document") String document) {
        List<StorageDto> storageDtoToSend = receiver.documentReceived(documentsWrapper, document);
        return new ResponseEntity<>(storageDtoToSend, HttpStatus.CREATED);
    }

    @GetMapping("/{report}")
    public ResponseEntity<List<StorageDto>> report(@PathVariable("report") String report) {
        List<StorageDto> storageDtosList = receiver.reportReceived(report);
        return new ResponseEntity<>(storageDtosList, HttpStatus.OK);
    }

}
