package doctorhoai.learn.userservice.exception.contanst;

import lombok.Getter;

@Getter
public enum EMessageException {

    // duplicate
    PHONE_NUMBER_DUPLICATE("Phone number is already in use"),
    EMAIL_DUPLICATE("Email is already in use"),
    CCCD_DUPLICATE("CCCD is already in use"),

    // not found
    ROLE_NOT_FOUND("Role not found"),
    USER_NOT_FOUND("User not found with %s : %s"),
    EMPLOYEE_NOT_FOUND("Employee not found "),
    PASSWORD_NOT_CORRECT("Password is not correct"),
    ;

    private String message;
    EMessageException(String message) {
        this.message = message;
    }
}
