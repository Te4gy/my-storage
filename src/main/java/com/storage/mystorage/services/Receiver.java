package com.storage.mystorage.services;

import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.utils.myDto.wrapperDto.DocumentsWrapper;
import com.storage.mystorage.utils.documents.Document;
import com.storage.mystorage.utils.reports.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Receiver {

    final List<Document> documentList;
    final List<Report> reportList;


    public List<StorageDto> documentReceived(DocumentsWrapper documentsWrapper, String documentName) {
        return documentList.stream()
                .filter(document -> document.checkDocument(documentName))
                .findFirst()
                .map(document -> document.process(documentsWrapper))
                .orElseGet(ArrayList::new);
    }

    public List<StorageDto> reportReceived(String reportName) {
        return reportList.stream()
                .filter(report -> report.checkReport(reportName))
                .findFirst()
                .map(Report::process)
                .orElseGet(ArrayList::new);
    }
}
