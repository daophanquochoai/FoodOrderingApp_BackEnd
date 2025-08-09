package doctorhoai.learn.aiservice.config;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaConfig {
    private static final String BASE_URL = "http://14.225.254.190:11434";
    private static final String MODEL_NAME = "qwen3:4b";
    private static final String MODE_EMBEDDING = "nomic-embed-text";

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        // Creates standard chat model for:
        // - Regular question-answering
        // - Single response generation
        // - Zero temperature for consistent outputs
        return OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .timeout(Duration.ofMinutes(10))
                .temperature(0.0)  // Deterministic responses
                .modelName(MODEL_NAME)
                .build();
    }

    @Bean
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        // Creates streaming model for:
        // - Real-time response generation
        // - Token-by-token output
        // - Better user experience with immediate feedback
        return OllamaStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .timeout(Duration.ofMinutes(10)) // Increased timeout for streaming
                .temperature(0.0)
                .modelName(MODEL_NAME)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        // Creates embedding model for:
        // - Converting text to vectors
        // - Document indexing
        // - Similarity search
        return OllamaEmbeddingModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODE_EMBEDDING)
                .timeout(Duration.ofMinutes(10)) // Increased timeout for embedding requests
                .build();
    }
}
