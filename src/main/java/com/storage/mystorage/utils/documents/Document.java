package com.storage.mystorage.utils.documents;

import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;

import java.util.List;

public interface Document {

    boolean checkDocument(String documentName);

    List<StorageDto> process(DocumentsWrapper documentsWrapper);
}
