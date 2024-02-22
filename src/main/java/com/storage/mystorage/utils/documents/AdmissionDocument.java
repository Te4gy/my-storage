package com.storage.mystorage.utils.documents;

import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdmissionDocument implements Document {

    private static final String ADMISSION = "admission";

    final DocumentService documentService;

    @Override
    public boolean checkDocument(String documentName) {
        return ADMISSION.equals(documentName);
    }

    @Override
    public List<StorageDto> process(DocumentsWrapper documentsWrapper) {
        return documentService.admission(documentsWrapper);
    }
}
