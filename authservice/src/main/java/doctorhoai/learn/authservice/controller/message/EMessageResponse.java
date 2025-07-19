package doctorhoai.learn.authservice.controller.message;

public enum EMessageResponse {

    LOGOUT_SUCCESSFUL("Logout successful"),
    TOKEN_VALIDATION_SUCCESSFUL("Token validation successful");

    private String message;
    EMessageResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
