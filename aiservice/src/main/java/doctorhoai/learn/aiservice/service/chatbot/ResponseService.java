package doctorhoai.learn.aiservice.service.chatbot;

import doctorhoai.learn.aiservice.model.ChatbotResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public ChatbotResponse createResponse(String threadId, String parentMessageId, List<String> contentList) {
        String fullResponse = String.join("", contentList);

        fullResponse = fullResponse.replaceAll("(?s)<think>.*?</think>", "").trim();

        ChatbotResponse response = new ChatbotResponse();
        ChatbotResponse.ResponseData responseData = new ChatbotResponse.ResponseData();
        ChatbotResponse.CopilotResponse copilotResponse = new ChatbotResponse.CopilotResponse();

        copilotResponse.setThreadId(threadId);
        copilotResponse.setRunId(String.valueOf(System.currentTimeMillis()));

        ChatbotResponse.OutputMessage message = new ChatbotResponse.OutputMessage();
        message.setId("msg-" + System.currentTimeMillis());
        message.setCreatedAt(java.time.Instant.now().toString());
        message.setContent(List.of(fullResponse));
        message.setRole("assistant");
        message.setParentMessageId(parentMessageId);

        ChatbotResponse.MessageStatus messageStatus = new ChatbotResponse.MessageStatus();
        messageStatus.setCode("SUCCESS");
        message.setStatus(messageStatus);

        ChatbotResponse.Status status = new ChatbotResponse.Status();
        status.setCode("SUCCESS");
        copilotResponse.setStatus(status);

        copilotResponse.setMessages(List.of(message));
        responseData.setGenerateCopilotResponse(copilotResponse);
        response.setData(responseData);

        return response;
    }
}
