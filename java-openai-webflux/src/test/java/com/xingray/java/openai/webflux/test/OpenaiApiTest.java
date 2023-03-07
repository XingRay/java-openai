package com.xingray.java.openai.webflux.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingray.java.openai.webflux.api.ApiBuilder;
import com.xingray.java.openai.webflux.api.OpenaiApi;
import com.xingray.java.openai.webflux.constants.Models;
import com.xingray.java.openai.webflux.constants.Roles;
import com.xingray.java.openai.webflux.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
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
        String apiKey = Secret.apiKey;
        String proxyHost = "localhost";
        int proxyPort = 10809;
        ProxyProvider.Proxy proxyType = ProxyProvider.Proxy.HTTP;

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
                        strings.forEach(new Consumer<String>() {
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
        ChatRequest request = new ChatRequest();
        request.setModel(Models.GPT);
//        request.setMessages(List.of(new Message("user", "用中文写一篇《浅析企业管理中的成本控制》的大纲")));
//        request.setMessages(List.of(new Message(Roles.USER, "请一个奇幻故事，100字以内"),
//                new Message(Roles.ASSISTANT, """
//                        在一个神秘的森林里，生活着一个小精灵。她有着漂亮的羽翼和闪耀的眼睛，非常聪明，擅长制作美丽的魔法珠宝。
//
//                        某天，小精灵发现一个她从未见过的魔法球，它散发着神秘的光彩。小精灵好奇的走近一看，突然被魔法球吸入了其中。
//
//                        当小精灵醒来时，她发现自己置身于一个绚烂的王国。这个王国由飞行的龙、流行的独角兽、翩翩起舞的鹤构成。他们都热情欢迎并帮助小精灵融入这个神奇的世界。
//
//                        在这里，小精灵学会了更多的魔法技巧，并与新朋友们分享了自己的魔法珠宝。她变得更加自信、善良和勇敢。最终，小精灵成功地找回了回到家的路，并将这个神奇的王国与人类分享。
//                        """),
//                new Message(Roles.USER, "请详细的讲述她在王国中的经历")));
        request.setMessages(List.of(new Message(Roles.USER, "去西藏旅游需要注意什么")));
        request.setStream(true);
        int n = 1;
        request.setN(n);
//        request.setStop(List.of("西藏","宗教","环境","饮食"));

        // temperature 0-2
//        request.setTemperature(0.2);
//        request.setTemperature(1.8);

        // top_P 0-1
//        request.setTopP(0.1);
//        request.setTopP(0.9);

        StringBuilder[] builders = new StringBuilder[n];
        for (int i = 0; i < n; i++) {
            builders[i] = new StringBuilder();
        }

        Flux<String> flux = openaiApi.chatCompletions(request);
//        flux.map(new Function<String, String>() {
//            @Override
//            public String apply(String s) {
//                System.out.println(s);
//                return s;
//            }
//        }).filter(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return !s.equals("[DONE]");
//            }
//        }).map(new Function<String, ChatResponse>() {
//            @Override
//            public ChatResponse apply(String s) {
//                try {
//                    ChatResponse chatResponse = objectMapper.readValue(s, ChatResponse.class);
////                    System.out.println(chatResponse);
//                    return chatResponse;
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).flatMapIterable(new Function<ChatResponse, Iterable<Choice>>() {
//            @Override
//            public Iterable<Choice> apply(ChatResponse chatResponse) {
//                List<Choice> choices = chatResponse.getChoices();
////                System.out.println(choices);
//                return choices;
//            }
//        }).filter(new Predicate<Choice>() {
//            @Override
//            public boolean test(Choice choice) {
//                Delta delta = choice.getDelta();
//                return delta != null && delta.getContent() != null;
//            }
//        }).map(new Function<Choice, String>() {
//            @Override
//            public String apply(Choice choice) {
//                return choice.getDelta().getContent();
//            }
//        }).buffer().subscribe(new Consumer<List<String>>() {
//            @Override
//            public void accept(List<String> strings) {
//                strings.forEach(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        System.out.print(s);
//                    }
//                });
//            }
//        });

//        flux.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                handleChatStreamResponse(s, builders);
//            }
//        });

        flux.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                handleChatStreamResponse(s, builders);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
                for (StringBuilder builder : builders) {
                    System.out.println(builder);
                }
            }
        });

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        stringList.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                handleChatStreamResponse(s);
//            }
//        });
    }

    private void handleChatStreamResponse(String s, StringBuilder[] builders) {
        System.out.println(s);
        if (s.equals("[DONE]")) {
            for (StringBuilder builder : builders) {
                builder.append('\n').append(s);
            }
            return;
        }

        try {
            ChatResponse chatResponse = objectMapper.readValue(s, ChatResponse.class);
            Choice choice = chatResponse.getChoices().get(0);
            Integer index = choice.getIndex();
            StringBuilder builder = builders[index];

            Delta delta = choice.getDelta();
            String finishReason = choice.getFinishReason();
            if (delta != null) {
                String content = delta.getContent();
                String role = delta.getRole();
                if (content != null) {
                    builder.append(content);
                    return;
                }
                if (role != null) {
                    builder.append("role:").append(role);
                    return;
                }
                return;
            }
            if (finishReason != null) {
                builder.append("finishReason:").append(finishReason);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}