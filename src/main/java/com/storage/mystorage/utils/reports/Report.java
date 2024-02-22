package com.storage.mystorage.utils.reports;

import com.storage.mystorage.myDto.answersDto.StorageDto;

import java.util.List;

public interface Report {

    boolean checkReport(String reportName);

    List<StorageDto> process();
}
