package doctorhoai.learn.authservice.business.inventoryservice.service.history_ingredients;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.HistoryImportOrExportDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventoryservice",
        path = "/history_import_or_export",
        contextId = "historyImportOrExportBusiness",
        fallbackFactory = HistoryFeignFallback.class,
        configuration = FeignConfig.class
)
public interface HistoryFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> addHistoryImportOrExport(
            @RequestBody @Valid HistoryImportOrExportDto historyImportOrExportDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> allHistoryImportOrExport(
            @RequestBody Filter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateHistoryImportOrExport(
            @PathVariable Integer id
    );
}
