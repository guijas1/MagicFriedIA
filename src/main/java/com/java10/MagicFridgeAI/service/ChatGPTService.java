package com.java10.MagicFridgeAI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.java10.MagicFridgeAI.model.FoodItem;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatGPTService {

    private final WebClient webClient;

    private String apiKey = System.getenv("API_KEY");

    public ChatGPTService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItem> foodItemList) {

        String alimentos = foodItemList.stream().map(item -> String.format("%s (%s) - Quantidades: %d, Validade: %s",
                item.getNome(),
                item.getCategoria(),
                item.getQtd(),
                item.getValidade())).collect(Collectors.joining("\n"));
        String prompt = "Faça uma receita com os seguintes itens:\n" + alimentos;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-5-nano",
                "input", prompt
        );

        return webClient.post()
                .uri("/responses")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    try {
                        // Caminho: output -> [1] -> content -> [0] -> text
                        JsonNode messageNode = json.path("output").get(1);
                        if (messageNode != null) {
                            JsonNode textNode = messageNode.path("content").get(0).path("text");
                            return textNode.asText();
                        }
                        return "Não foi possível extrair o texto da resposta 😕";
                    } catch (Exception e) {
                        return "Erro ao processar resposta da API: " + e.getMessage();
                    }
                });
    }
}
