package openai.learn.openai.learn.chat.completion.service;

import openai.learn.openai.earn.chat.completion.client.OpenAIClient;
import openai.learn.openai.earn.chat.completion.service.ChatCompletionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatCompletionServiceTest {

    @Mock
    private OpenAIClient openAIClient;

    @InjectMocks
    private ChatCompletionService chatCompletionService;

    @Test
    public void testChat() {
        String input = "Hello, world!";
        List<String> expectedResponse = Arrays.asList("Hi there!", "Hello!");

        when(openAIClient.chat(input)).thenReturn(expectedResponse);

        List<String> actualResponse = chatCompletionService.chat(input);
        assertEquals(expectedResponse, actualResponse);
    }
}