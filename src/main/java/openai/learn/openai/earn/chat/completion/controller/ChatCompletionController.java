package openai.learn.openai.earn.chat.completion.controller;

import openai.learn.openai.earn.chat.completion.service.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatCompletionController {

    @Autowired
    private ChatCompletionService chatCompletionService;

    @GetMapping("/chat")
    public List<String> doChatting(@RequestParam("input") final String input) {
        return chatCompletionService.chat(input);
    }
}
