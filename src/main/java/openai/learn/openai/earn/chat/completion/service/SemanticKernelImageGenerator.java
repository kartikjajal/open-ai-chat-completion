package openai.learn.openai.earn.chat.completion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import openai.learn.openai.earn.chat.completion.config.OpenAIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
@Slf4j
public class SemanticKernelImageGenerator {
    private static final String API_URL = "https://ai-proxy.lab.epam.com/openai/deployments/";
    public static final String JSON_PATH_IMAGE_URL = "/choices/0/message/custom_content/attachments/1/url";

    @Autowired
    private OpenAIConfiguration configuration;

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String generateImage(String prompt) {
        var requestBody = new HashMap<>();
        requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
        requestBody.put("max_tokens", 1000);

        var requestJson = objectMapper.writeValueAsString(requestBody);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(configuration.getEndpoint()+"/openai/deployments/"+
                                configuration.getDeploymentNameImageGeneration() +
                        "/chat/completions?api-version=2023-12-01-preview"))
                .header("Content-Type", "application/json")
                .header("Api-Key", configuration.getOpenAIKey())
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            var jsonResponse = objectMapper.readTree(response.body());
            return jsonResponse.at(JSON_PATH_IMAGE_URL).asText();
        } else {
            throw new RuntimeException("Failed to generate image: " + response.body());
        }
    }
}
