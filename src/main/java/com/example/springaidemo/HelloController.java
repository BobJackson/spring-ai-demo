package com.example.springaidemo;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${spring.ai.ollama.chat.model}")
    private String model;

    private final OllamaApi api;

    public HelloController(OllamaApi api) {
        this.api = api;
    }

    @GetMapping("/ai/generate")
    public OllamaApi.GenerateResponse embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        OllamaApi.GenerateRequest request = OllamaApi.GenerateRequest.builder(message)
                .withStream(false)
                .withModel(model)
                .build();
        return api.generate(request);
    }


}