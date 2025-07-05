package doctorhoai.learn.userservice.logging;

import lombok.extern.slf4j.Slf4j;
import io.sentry.protocol.User;
import io.sentry.Sentry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class Logging {

    /**
     * to send an exception to sentry
     *
     * @param message : thong tin ve loi
     *        exception : loi xay ra
     */
    protected void sendException( Exception exception,String... message) {
        Sentry.captureException(exception);
        Sentry.captureMessage(exception.getMessage() + " " + message);
    }

    /**
     * to send an info error to sentry
     *
     * @param message : thong tin ve loi
     */
    protected void sendInfo(String... message) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Sentry.captureMessage(methodName + Arrays.toString(message));
    }
}
