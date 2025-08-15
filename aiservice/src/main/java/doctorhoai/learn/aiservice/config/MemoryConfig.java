package doctorhoai.learn.aiservice.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    private EmbeddingModel embeddingModel;
    @Value("${memory.domain}")
    private String domain;
    @Value("${memory.port}")
    private Integer port;
    @Value("${memory.db}")
    private String db;
    @Value("${memory.username}")
    private String username;
    @Value("${memory.password}")
    private String password;
    @Value("${memory.table}")
    private String table;

    public MemoryConfig(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    //store history of chat messages
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return chatId -> MessageWindowChatMemory.withMaxMessages(2);
    }


    // define the embedding store bean
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return PgVectorEmbeddingStore.builder()
                .host(domain)
                .port(port)
                .database(db)
                .user(username)
                .password(password)
                .table(table)
                .dimension(embeddingModel.dimension())  // Automatically matches model's embedding size
                .createTable(true)  // Creates table if it doesn't exist
                .build();
    }
}
