package openai.learn.openai.earn.chat.completion.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
