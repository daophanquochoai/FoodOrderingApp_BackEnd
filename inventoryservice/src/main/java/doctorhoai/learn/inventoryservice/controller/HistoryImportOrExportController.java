package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.HistoryImportOrExportDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.service.history_import_or_export.HistoryImportOrExportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history_import_or_export")
@RequiredArgsConstructor
public class HistoryImportOrExportController {

    private final HistoryImportOrExportService historyImportOrExportService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addHistoryImportOrExport(
            @RequestBody HistoryImportOrExportDto historyImportOrExportDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(historyImportOrExportService.createHistoryImportOrExport(historyImportOrExportDto))
                        .message(EMessageResponse.CREATE_HISTORY_IMPORT_OR_EXPORT.getMessage())
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> allHistoryImportOrExport(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_HISTORY_IMPORT_OR_EXPORT.getMessage())
                        .data(historyImportOrExportService.getHistoryImportOrExportByFilter(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateHistoryImportOrExport(
            @PathVariable Integer id
    ){
        historyImportOrExportService.updateHistoryImportOrExport(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_HISTORY_IMPORT_OR_EXPORT.getMessage())
                        .build()
        );
    }
}
