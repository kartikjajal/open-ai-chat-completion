package openai.learn.openai.earn.chat.completion.service;

import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.client.OpenAIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PromptService {

    @Autowired
    private OpenAIClient openAIClient;

    public List<String> chat(final String input) {
        List<String> messages = openAIClient.chat(input);
        log.info("Return Choices : {}", messages);
        return messages;
    }
}
