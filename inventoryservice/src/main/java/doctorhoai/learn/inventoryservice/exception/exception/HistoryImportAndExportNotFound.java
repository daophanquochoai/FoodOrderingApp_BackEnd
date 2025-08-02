package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class HistoryImportAndExportNotFound extends NotFound {
    public HistoryImportAndExportNotFound() {
        super(EMessageException.HISTORY_IMPORT_NOT_FOUND.getMessage());
    }
}
