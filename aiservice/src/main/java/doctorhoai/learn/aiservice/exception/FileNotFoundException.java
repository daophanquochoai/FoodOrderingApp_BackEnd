package doctorhoai.learn.aiservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class FileNotFoundException extends NotFound {
    public FileNotFoundException() {
        super(EMessageException.FILE_NOT_FOUND.getMessage());
    }
}
