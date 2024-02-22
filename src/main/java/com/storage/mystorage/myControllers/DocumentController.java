package com.storage.mystorage.myControllers;

import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myDto.wrapperDto.DocumentsWrapper;
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

    final DocumentService documentService;
    final Receiver receiver;

    @PostMapping("/{document}")
    public ResponseEntity<List<StorageDto>> document(@RequestBody DocumentsWrapper documentsWrapper,
                                                     @PathVariable("document") String document) {
        List<StorageDto> storageDtoToSend = receiver.documentReceived(documentsWrapper, document);
        return new ResponseEntity<>(storageDtoToSend, HttpStatus.OK);
    }

}
