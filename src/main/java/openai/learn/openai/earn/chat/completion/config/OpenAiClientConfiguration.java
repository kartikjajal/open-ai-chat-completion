package openai.learn.openai.earn.chat.completion.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenAiClientConfiguration {

    @Autowired
    private OpenAIConfiguration configuration;
    /**
     * Creates an {@link OpenAIAsyncClient} bean for interacting with Azure OpenAI Service asynchronously.
     *
     * @return an instance of {@link OpenAIAsyncClient}
     */
    @Bean
    public OpenAIAsyncClient openAIAsyncClient() {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(configuration.getOpenAIKey()))
                .endpoint(configuration.getEndpoint())
                .buildAsyncClient();
    }


    @Bean
    public ChatCompletionService chatCompletionService(OpenAIAsyncClient openAIAsyncClient) {
        return OpenAIChatCompletion.builder()
                .withModelId(configuration.getDeploymentName())
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build();
    }

    @Bean
    public Kernel kernel(ChatCompletionService chatCompletionService) {
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .build();
    }

    @Bean
    public InvocationContext invocationContext() {
        return InvocationContext.builder()
                .withPromptExecutionSettings(PromptExecutionSettings.builder()
                        .withTemperature(configuration.getTemperature())
                        .withMaxTokens(configuration.getMaxTokens())
                        .withPresencePenalty(configuration.getPresencePenalty())
                        .withFrequencyPenalty(configuration.getFrequencyPenalty())
                        .build())
                .build();
    }

    @Bean
    public Map<String, PromptExecutionSettings> promptExecutionsSettingsMap() {
        return Map.of(configuration.getDeploymentName(), PromptExecutionSettings.builder()
                .withTemperature(configuration.getTemperature())
                .withMaxTokens(configuration.getMaxTokens())
                .withPresencePenalty(configuration.getPresencePenalty())
                .withFrequencyPenalty(configuration.getFrequencyPenalty())
                .build());
    }
}
