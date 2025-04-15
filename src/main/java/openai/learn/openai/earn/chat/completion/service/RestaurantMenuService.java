package openai.learn.openai.earn.chat.completion.service;

import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatMessageContent;
import com.microsoft.semantickernel.contextvariables.ContextVariableTypeConverter;
import com.microsoft.semantickernel.contextvariables.ContextVariableTypes;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import openai.learn.openai.earn.chat.completion.model.RestaurantMenu;
import openai.learn.openai.earn.chat.completion.plugin.RestaurantPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantMenuService {

    @Autowired
    private ChatCompletionService chatCompletionService;

    public List<String> getMenu(String item){

        addContextVariableTypeConverter(RestaurantMenu.class);
        // Import the LightsPlugin
        KernelPlugin menuPlugin = KernelPluginFactory.createFromObject(new RestaurantPlugin(),
                "RestaurantMenuPlugin");

        // Create a kernel with Azure OpenAI chat completion and plugin
        Kernel kernel = Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .withPlugin(menuPlugin)
                .build();

        // Enable planning
        InvocationContext invocationContext = new InvocationContext.Builder()
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                .build();

        // Create a history to store the conversation
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addUserMessage(String.format("Get Menu for item %s", item));


        List<ChatMessageContent<?>> results = chatCompletionService.getChatMessageContentsAsync(
                chatHistory,
                kernel,
                invocationContext
        ).block();

        System.out.println("Assistant > " + results.get(0));
        assert results != null;
        chatHistory.addAll(results);
        //return List.of(result);
        return results.stream()
                .map(ChatMessageContent::getContent)
                .collect(Collectors.toList());
    }

    private <T> void addContextVariableTypeConverter(Class<T> type) {
        ContextVariableTypes.addGlobalConverter(
                ContextVariableTypeConverter
                        .builder(type)
                        .toPromptString(new Gson()::toJson)
                        .build()
        );
    }
}
