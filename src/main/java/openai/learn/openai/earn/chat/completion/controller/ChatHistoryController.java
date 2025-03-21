package openai.learn.openai.earn.chat.completion.controller;

import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.service.ChatHistoryPromptService;
import openai.learn.openai.earn.chat.completion.service.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class ChatHistoryController {

    @Autowired
    private ChatHistoryPromptService promptService;

    final HashMap<String, ChatHistory> chatHistoryCache = new HashMap<>();


    @GetMapping("/chat-history")
    public List<String> doChatting(@RequestParam("input") final String input,
                                   @RequestParam(name = "chatId", required = false) String chatId) {
        return promptService.getChatCompletions(getChatHistory(input, chatId));
    }

    private ChatHistory getChatHistory(String input, String chatId){
        // Each chatId with its own context
        var chatHistory = this.chatHistoryCache.get(chatId);
        if(chatHistory != null) {
            log.info("Chat id {} with history found." , chatId);
            chatHistory.addUserMessage(input);
            return chatHistory;
        }else {
            log.info("Creating new chat history with chat Id {}." , chatId);
            var newChat = new ChatHistory();
            newChat.addUserMessage(input);
            this.chatHistoryCache.put(chatId, newChat);
            return newChat;
        }
    }
}
