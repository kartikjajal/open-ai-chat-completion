package openai.learn.openai.earn.chat.completion.service;

import com.azure.ai.openai.models.Embeddings;
import io.qdrant.client.*;
import io.qdrant.client.grpc.Points;
import lombok.SneakyThrows;
import openai.learn.openai.earn.chat.completion.client.OpenAIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmbeddingsService {
    private static final String COLLECTION_NAME = "openai_vectors";
    @Autowired
    private OpenAIClient openAIClient;

    @Autowired
    private QdrantClient qdrantClient;

    @SneakyThrows
    public Embeddings getEmbeddingsAndSave(final String input) {
        Embeddings embeddings = openAIClient.getEmbeddings(input);

        List<Float> points = new ArrayList<>(embeddings.getData()
                .stream().findFirst()
                .get().getEmbedding());
        Points.PointStruct pointStruct = Points.PointStruct.newBuilder()
                .setId(PointIdFactory.id(UUID.randomUUID()))
                .setVectors(VectorsFactory.vectors(points))
                .putAllPayload(Map.of("info", ValueFactory.value(input)))
                .build();
        qdrantClient.upsertAsync(COLLECTION_NAME,List.of(pointStruct)).get();

        return embeddings;
    }

    @SneakyThrows
    public List<Points.ScoredPoint> searchEmbeddings(final String input) {
        Embeddings embeddings = openAIClient.getEmbeddings(input);

        List<Float> points = new ArrayList<>(embeddings.getData()
                .stream().findFirst()
                .get().getEmbedding());
        
        return qdrantClient.searchAsync(Points.SearchPoints.newBuilder()
                        .setCollectionName(COLLECTION_NAME)
                        .addAllVector(points)
                        .setWithPayload(WithPayloadSelectorFactory.enable(true))
                        .setLimit(2)
                        .build())
                .get();
    }
}
