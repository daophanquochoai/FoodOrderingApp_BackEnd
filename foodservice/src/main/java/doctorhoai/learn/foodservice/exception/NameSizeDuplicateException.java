package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.Duplicate;

public class NameSizeDuplicateException extends Duplicate {
  public NameSizeDuplicateException() {
    super(EMessageException.NAME_SIZE_DUPLICATE.getMessage());
  }
}
