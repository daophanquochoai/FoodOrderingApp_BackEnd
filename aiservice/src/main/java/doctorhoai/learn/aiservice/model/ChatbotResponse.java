package doctorhoai.learn.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotResponse {
    private ResponseData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseData {
        private CopilotResponse generateCopilotResponse;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CopilotResponse {
        private String threadId;
        private String runId;
        private List<OutputMessage> messages;
        private Status status;
        private final String __typename = "CopilotResponse";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutputMessage {
        private String id;
        private String createdAt;
        private List<String> content;
        private String role;
        private String parentMessageId;
        private MessageStatus status;
        private final String __typename = "TextMessageOutput";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status {
        private String code;
        private final String __typename = "BaseResponseStatus";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageStatus {
        private String code;
        private final String __typename = "SuccessMessageStatus";
    }
}
