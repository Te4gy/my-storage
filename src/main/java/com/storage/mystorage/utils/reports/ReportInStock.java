package com.storage.mystorage.utils.reports;

import com.storage.mystorage.myDto.answersDto.StorageDto;
import com.storage.mystorage.services.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportInStock implements Report {

    private static final String REPORT_IN_STOCK = "inStockProduct";

    final ReportsService reportsService;

    @Override
    public boolean checkReport(String reportName) {
        return REPORT_IN_STOCK.equals(reportName);
    }

    @Override
    public List<StorageDto> process() {
        return reportsService.inStockProducts();
    }
}
