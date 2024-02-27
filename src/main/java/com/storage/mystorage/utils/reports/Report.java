package com.storage.mystorage.utils.reports;

import com.storage.mystorage.utils.dto.answersDto.StorageDto;

import java.util.List;

public interface Report {

    boolean checkReport(String reportName);

    List<StorageDto> process();
}
