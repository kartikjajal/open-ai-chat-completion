package openai.learn.openai.earn.chat.completion.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatHistoryPromptService {

    @Autowired
    private ChatCompletionService chatCompletionService;

    @Autowired
    private Kernel kernel;

    @Autowired
    private InvocationContext invocationContext;

    public List<String> getChatCompletions(ChatHistory chatHistory){
        List<ChatMessageContent<?>> results = chatCompletionService.getChatMessageContentsAsync(
                chatHistory,
                kernel,
                invocationContext
        ).block();

        assert results != null;
        chatHistory.addAll(results);
        return results.stream().map(ChatMessageContent::getContent).toList();
    }
}
