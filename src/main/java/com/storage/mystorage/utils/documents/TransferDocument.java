package com.storage.mystorage.utils.documents;

import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransferDocument implements Document {

    private static final String TRANSFER = "transfer";


    final DocumentService documentService;

    @Override
    public boolean checkDocument(String documentName) {
        return TRANSFER.equals(documentName);
    }

    @Override
    public List<StorageDto> process(DocumentsWrapper documentsWrapper) {
        return documentService.transfer(documentsWrapper);
    }
}
