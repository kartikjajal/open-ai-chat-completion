package openai.learn.openai.learn.chat.completion.client;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatResponseMessage;
import openai.learn.openai.earn.chat.completion.client.OpenAIClient;
import openai.learn.openai.earn.chat.completion.config.OpenAIConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OpenAIClientTest {

    @Mock
    private OpenAIConfiguration configuration;

    @Mock
    private OpenAIAsyncClient openAIAsyncClient;

    @InjectMocks
    private OpenAIClient openAIClient;

    @BeforeEach
    public void setUp() {
        when(configuration.getOpenAIKey()).thenReturn("test-key");
        when(configuration.getDeploymentName()).thenReturn("test-deployment");
    }

    @Test
    public void testChat() {
        // Arrange
        String input = "Hello, world!";
        ChatResponseMessage responseMessage = mock(ChatResponseMessage.class);
        when(responseMessage.getContent()).thenReturn("response");

        ChatChoice chatChoice = mock(ChatChoice.class);
        when(chatChoice.getMessage()).thenReturn(responseMessage);

        ChatCompletions chatCompletions = mock(ChatCompletions.class);
        when(chatCompletions.getChoices()).thenReturn(List.of(chatChoice));

        when(openAIAsyncClient.getChatCompletions(any(), any(ChatCompletionsOptions.class)))
                .thenReturn(Mono.just(chatCompletions));

        // Act
        List<String> result = openAIClient.chat(input);

        // Assert
        assertEquals(List.of("response"), result);
        verify(openAIAsyncClient, times(1)).getChatCompletions(eq("test-deployment"), any(ChatCompletionsOptions.class));
    }

    @Test
    public void testChatWithNoCompletions() {
        // Arrange
        String input = "Hello, world!";
        when(openAIAsyncClient.getChatCompletions(any(), any(ChatCompletionsOptions.class)))
                .thenReturn(Mono.empty());

        // Act
        List<String> result = openAIClient.chat(input);

        // Assert
        assertEquals(List.of(), result);
        verify(openAIAsyncClient, times(1)).getChatCompletions(eq("test-deployment"), any(ChatCompletionsOptions.class));
    }
}