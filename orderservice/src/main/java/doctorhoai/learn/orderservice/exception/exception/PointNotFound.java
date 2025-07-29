package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class PointNotFound extends NotFound {
    public PointNotFound() {
        super(EMessageException.POINT_OF_USER_NOT_FOUND.getMessage());
    }
}
