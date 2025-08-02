package doctorhoai.learn.authservice.business.inventoryservice.service.history_ingredients;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.HistoryImportOrExportDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryFeignFallback implements FallbackFactory<HistoryFeign> {

    private final HandleFallBack fallBack;


    @Override
    public HistoryFeign create(Throwable cause) {
        return new HistoryFeign() {
            @Override
            public ResponseEntity<ResponseObject> addHistoryImportOrExport(HistoryImportOrExportDto historyImportOrExportDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> allHistoryImportOrExport(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateHistoryImportOrExport(Integer id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
