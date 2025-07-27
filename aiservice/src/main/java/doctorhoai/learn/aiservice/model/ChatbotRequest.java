package doctorhoai.learn.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotRequest {
    private String operationName;
    private Variables variables;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Variables {
        private ChatData data;
        private Map<String, Object> properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatData {
        private String threadId;
        private List<Message> messages;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String id;
        private TextMessage textMessage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextMessage {
        private String content;
        private String role;
    }
}
