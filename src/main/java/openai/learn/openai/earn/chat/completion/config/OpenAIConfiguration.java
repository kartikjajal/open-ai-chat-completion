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

    @Value("${client-openai-deployment-name-image-generation}")
    private String deploymentNameImageGeneration;



    @Value("${prompt-config-temperature}")
    private double temperature;

    @Value("${prompt-config-presence-max_tokens}")
    private int maxTokens;

    @Value("${prompt-config-presence-penalty}")
    private double presencePenalty;

    @Value("${prompt-config-frequency-penalty}")
    private double frequencyPenalty;
}