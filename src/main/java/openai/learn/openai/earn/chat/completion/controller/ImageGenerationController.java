package openai.learn.openai.earn.chat.completion.controller;

import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.service.ChatHistoryPromptService;
import openai.learn.openai.earn.chat.completion.service.SemanticKernelImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class ImageGenerationController {

    @Autowired
    private SemanticKernelImageGenerator imageGenerator;

    final HashMap<String, ChatHistory> chatHistoryCache = new HashMap<>();


    @GetMapping("/generate-image")
    public String generateImage(@RequestParam("input") final String input) {
        return imageGenerator.generateImage(input);
    }
}
