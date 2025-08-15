package doctorhoai.learn.userservice.controller.contanst;

import lombok.Data;
import lombok.Getter;

@Getter
public enum EMessageResponse {
    //user
    GET_USER("Get user successful"),
    UPDATE_USER("Update user successful"),
    ADD_USER("Save user successful"),
    UPDATE_PASSWORD_SUCCESSFUL("Update password successful"),

    //employee
    GET_EMPLOYEE("Get employee successful"),
    ADD_EMPLOYEE("Add employee successful"),
    UPDATE_EMPLOYEE("Update employee successful"),

    // address
    GET_ADDRESS_SUCCESS("Get address successful"),
    ADD_ADDRESS("Add address successful"),
    UPDATE_ADDRESS("Update address successful"),
    ;

    private String message;
    EMessageResponse( String message ) {
        this.message = message;
    }
}
