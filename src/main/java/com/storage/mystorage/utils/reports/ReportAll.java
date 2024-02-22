package com.storage.mystorage.utils.reports;

import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.services.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ReportAll implements Report {

    private static final String REPORT_ALL = "allProducts";

    final ReportsService reportsService;

    @Override
    public boolean checkReport(String reportName) {
        return REPORT_ALL.equals(reportName);
    }

    @Override
    public List<StorageDto> process() {
        return reportsService.allProducts();
    }
}
