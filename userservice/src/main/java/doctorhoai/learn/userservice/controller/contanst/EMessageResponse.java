package doctorhoai.learn.userservice.controller.contanst;

import lombok.Data;
import lombok.Getter;

@Getter
public enum EMessageResponse {
    //user
    GET_USER("Get user successful"),
    UPDATE_USER("Update user successful"),
    ADD_USER("Save user successful"),

    //employee
    GET_EMPLOYEE("Get employee successful"),
    ADD_EMPLOYEE("Add employee successful"),
    UPDATE_EMPLOYEE("Update employee successful"),
    ;

    private String message;
    EMessageResponse( String message ) {
        this.message = message;
    }
}
