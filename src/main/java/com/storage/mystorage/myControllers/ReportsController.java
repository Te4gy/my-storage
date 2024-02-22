package com.storage.mystorage.myControllers;

import com.storage.mystorage.utils.myDto.answersDto.StorageDto;
import com.storage.mystorage.services.Receiver;
import com.storage.mystorage.services.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportsController {

    final ReportsService reportsService;
    final Receiver receiver;

    @GetMapping("/{report}")
    public ResponseEntity<List<StorageDto>> report(@PathVariable("report") String report) {
        List<StorageDto> storageDtosList = receiver.reportReceiver(report);
        return new ResponseEntity<>(storageDtosList, HttpStatus.OK);
    }
}
