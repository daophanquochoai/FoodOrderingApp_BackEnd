package doctorhoai.learn.aiservice.controller;

import lombok.Getter;

@Getter
public enum EMessageResponse {
    UPLOAD_SUCCESSFUL("Upload document successful"),
    GET_DOCUMENT_SUCCESSFUL("Get document successful"),
    GET_SEARCH_SUCCESSFUL("Get search successful"),
    DOWNLOAD_SUCCESSFUL("Download document successful");


    private final String message;
    EMessageResponse(String message) {
        this.message = message;
    }
}
