package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.Duplicate;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class HistoryImportDuplicate extends Duplicate {
    public HistoryImportDuplicate() {
        super(EMessageException.HISTORY_IMPORT_DUPLICATE.getMessage());
    }
}
