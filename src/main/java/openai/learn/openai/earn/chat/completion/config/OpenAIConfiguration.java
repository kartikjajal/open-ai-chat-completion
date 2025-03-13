package openai.learn.openai.earn.chat.completion.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OpenAIConfiguration {
    @Value("${client-openai-key}")
    private String openAIKey;

    @Value("${client-openai-endpoint}")
    private String endpoint;

    @Value("${client-openai-deployment-name}")
    private String deploymentName;
}