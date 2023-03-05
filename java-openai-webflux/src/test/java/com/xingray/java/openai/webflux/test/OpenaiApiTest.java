package com.xingray.java.openai.webflux.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingray.java.openai.webflux.api.ApiBuilder;
import com.xingray.java.openai.webflux.api.OpenaiApi;
import com.xingray.java.openai.webflux.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.transport.ProxyProvider;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class OpenaiApiTest {
    private OpenaiApi openaiApi;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        String apiKey = "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";
        String orgId = "org-66szOlOihkRkY6GBBoMNdfcR";
        String proxyHost = "localhost";
        int proxyPort = 10809;
        ProxyProvider.Proxy proxyType = ProxyProvider.Proxy.HTTP;

        objectMapper = new ObjectMapper();
        openaiApi = new ApiBuilder(apiKey, proxyHost, proxyPort, proxyType).openaiApi();
    }

    @Test
    void textCompletion() {
        OpenaiParam openaiParam = new OpenaiParam();
        openaiParam.setModel("text-davinci-003");
        openaiParam.setStream(true);
        openaiParam.setPrompt("Say this is a test");
        openaiParam.setTemperature(0);
        openaiParam.setMaxTokens(7);

        Flux<String> openAiResponseFlux = openaiApi.textCompletion(openaiParam);

        Flux<String> flux = openAiResponseFlux.filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
//                System.out.println(s);
                return !s.equals("[DONE]");
            }
        }).map(new Function<String, TextCompletionResponse>() {
            @Override
            public TextCompletionResponse apply(String s) {
                try {
                    return objectMapper.readValue(s, TextCompletionResponse.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }).flatMapIterable(new Function<TextCompletionResponse, Iterable<TextCompletionResponse.Choices>>() {
            @Override
            public Iterable<TextCompletionResponse.Choices> apply(TextCompletionResponse openAiResponse) {
                return openAiResponse.getChoices();
            }
        }).map(new Function<TextCompletionResponse.Choices, String>() {
            @Override
            public String apply(TextCompletionResponse.Choices choices) {
                return choices.getText();
            }
        });

        flux.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        flux.blockLast();
    }

    @Test
    void models() {
        Flux<ModelResponse> flux = openaiApi.models()
                .buffer()
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) {
                        StringBuilder stringBuilder = new StringBuilder();
                        strings.stream().forEach(new Consumer<String>() {
                            @Override
                            public void accept(String s) {
                                stringBuilder.append(s);
                            }
                        });
                        return stringBuilder.toString();
                    }
                })
                .map(new Function<String, ModelResponse>() {
                    @Override
                    public ModelResponse apply(String s) {
                        try {
                            return objectMapper.readValue(s, ModelResponse.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        System.out.println(flux.blockLast());
    }

    @Test
    void getModel() {
        Mono<Model> model = openaiApi.getModel("text-davinci-003");
        System.out.println(model.block());
    }

    @Test
    void completions() {
        String paramString = """
                {
                  "model": "text-davinci-003",
                  "prompt": "Say this is a test",
                  "max_tokens": 7,
                  "temperature": 0,
                  "top_p": 1,
                  "n": 1,
                  "stream": false,
                  "logprobs": null,
                  "stop": "\\n"
                }
                """;
        CompletionParam completionParam = null;
        try {
            completionParam = objectMapper.readValue(paramString, CompletionParam.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Mono<CompletionResponse> mono = openaiApi.completions(completionParam);

        CompletionResponse response = mono.block();
        System.out.println(response);
    }

    @Test
    void chatCompletions() {
        String paramString = """
                {
                  "model": "gpt-3.5-turbo",
                  "messages": [{"role": "user", "content": "Hello!"}]
                } 
                """;
        ChatCompletionParam param = null;
        try {
            param = objectMapper.readValue(paramString, ChatCompletionParam.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Flux<ChatResponse> flux = openaiApi.chatCompletions(param);
        flux.subscribe(new Consumer<ChatResponse>() {
            @Override
            public void accept(ChatResponse chatResponse) {
                System.out.println(chatResponse);
            }
        });

        flux.blockLast();
    }
}