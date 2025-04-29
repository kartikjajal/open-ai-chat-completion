package openai.learn.openai.earn.chat.completion.controller;

import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;
import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.client.OpenAIClient;
import openai.learn.openai.earn.chat.completion.model.DataInput;
import openai.learn.openai.earn.chat.completion.service.EmbeddingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/rag")
public class RagController {
    @Autowired
    private EmbeddingsService embeddingsService;

    @Autowired
    private OpenAIClient openAIClient;

    private static final String PROMPT_TEMPLATE = """
            Your task is to answer questions about design pattern. Use the information from the DOCUMENT section to provide
            accurate answers. If unsure of the answer or not found it, simply say that you don't know the answer.
            
            QUESTIONS:
            {INPUT}
            
            DOCUMENTS:
            {DOCUMENTS} 
            """;

    @PostMapping
    public void postValue(@RequestBody final DataInput dataInput){
      log.info(dataInput.getValue());
      embeddingsService.getEmbeddingsAndSave(dataInput.getValue());
    }

    @PostMapping("/retrieve")
    public List<String> retrieveValue(@RequestBody final DataInput dataInput){
        log.info(dataInput.getValue());
        List<Points.ScoredPoint> scoredPoints = embeddingsService.searchEmbeddings(dataInput.getValue());
        log.info("score : {}", scoredPoints.stream()
                .map(Points.ScoredPoint::getPayloadMap)
                .map(Map::values)
                .collect(Collectors.toSet()));
        Set<String> context = scoredPoints.stream()
                .map(Points.ScoredPoint::getPayloadMap)
                .map(Map::values)
                .map(values -> values.toString())
                .collect(Collectors.toSet());
        log.info(context.toString());

        return openAIClient.chat(PROMPT_TEMPLATE
                                    .replace("{INPUT}", dataInput.getValue())
                                    .replace("{DOCUMENTS}", context.toString()));
    }
}
