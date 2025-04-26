package openai.learn.openai.earn.chat.completion.controller;

import com.azure.ai.openai.models.Embeddings;
import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;
import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.service.EmbeddingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/embeddings")
@Slf4j
public class EmbeddingsController {

    @Autowired
    private EmbeddingsService embeddingsService;

    @GetMapping
    public Embeddings getEmbeddingsAndSave(@RequestParam("input") String input) {
        return embeddingsService.getEmbeddingsAndSave(input);
    }

    @GetMapping("/search")
    public Map<Collection<JsonWithInt.Value>, Float> searchEmbeddings(@RequestParam("input") String input) {
        List<Points.ScoredPoint> scoredPoints = embeddingsService.searchEmbeddings(input);
        final Map<Collection<JsonWithInt.Value>, Float> scoreMap = new HashMap<>();

        scoredPoints.forEach(
                score-> {
                    log.info("score : {}", score);
                    scoreMap.put(score.getPayloadMap().values(), score.getScore());
                }
        );

        return scoreMap;
    }
}
