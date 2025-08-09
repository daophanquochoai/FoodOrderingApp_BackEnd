package doctorhoai.learn.aiservice.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    private EmbeddingModel embeddingModel;

    public MemoryConfig(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    //store history of chat messages
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return chatId -> MessageWindowChatMemory.withMaxMessages(10);
    }


    // define the embedding store bean
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return PgVectorEmbeddingStore.builder()
                .host("14.225.254.190")
                .port(5432)
                .database("postgres")
                .user("my_user")
                .password("my_password")
                .table("my_embeddings")
                .dimension(embeddingModel.dimension())  // Automatically matches model's embedding size
                .createTable(true)  // Creates table if it doesn't exist
                .build();
    }
}
