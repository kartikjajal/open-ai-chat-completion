package openai.learn.openai.earn.chat.completion.client;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.config.OpenAIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OpenAIClient {
    @Autowired
    private OpenAIConfiguration configuration;

    @Autowired
    private OpenAIAsyncClient openAIAsyncClient;

    public List<String> chat(final String input) {
        log.info(configuration.getOpenAIKey());
        ChatCompletions chatCompletions = openAIAsyncClient.getChatCompletions(configuration.getDeploymentName(),
                                            new ChatCompletionsOptions(List.of(new ChatRequestUserMessage(input))))
                .block();

        return Optional.ofNullable(chatCompletions)
                .map(ChatCompletions::getChoices)
                .orElse(Collections.emptyList())
                .stream()
                .map(ChatChoice::getMessage)
                .map(ChatResponseMessage::getContent)
                .toList();
    }
}
