package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.HistoryImportOrExportDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.history_ingredients.HistoryFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("history_import_or_export")
public class HistoryController {
    private final HistoryFeign historyFeign;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addHistoryImportOrExport(
            @RequestBody HistoryImportOrExportDto historyImportOrExportDto
    ){
        return historyFeign.addHistoryImportOrExport(historyImportOrExportDto);
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> allHistoryImportOrExport(
            @RequestBody Filter filter
    ){
        return historyFeign.allHistoryImportOrExport(filter);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateHistoryImportOrExport(
            @PathVariable Integer id
    ){
        return historyFeign.updateHistoryImportOrExport(id);
    }
}
