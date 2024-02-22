package com.storage.mystorage.utils.documents;

import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SellDocument implements Document {

    private static final String SELL = "sell";

    final DocumentService documentService;

    @Override
    public boolean checkDocument(String documentName) {
        return SELL.equals(documentName);
    }

    @Override
    public List<StorageDto> process(DocumentsWrapper documentsWrapper) {
        return documentService.sell(documentsWrapper);
    }
}
