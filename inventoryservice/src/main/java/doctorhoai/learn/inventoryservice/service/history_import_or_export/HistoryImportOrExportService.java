package doctorhoai.learn.inventoryservice.service.history_import_or_export;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.HistoryImportOrExportDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;

public interface HistoryImportOrExportService {

    PageObject getHistoryImportOrExportByFilter(Filter filter);

    HistoryImportOrExportDto createHistoryImportOrExport(HistoryImportOrExportDto historyImportOrExportDto);

}
