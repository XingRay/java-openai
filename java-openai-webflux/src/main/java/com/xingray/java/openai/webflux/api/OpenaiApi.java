package com.xingray.java.openai.webflux.api;

import com.xingray.java.openai.webflux.entity.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange(accept = "application/json")
public interface OpenaiApi {

    @PostExchange(url = "/v1/completions")
    Flux<String> textCompletion(@RequestBody OpenaiParam param);

    @GetExchange(url = "/v1/models")
    Flux<String> models();

    @GetExchange(url = "/v1/models/{model}")
    Mono<Model> getModel(@PathVariable("model") String model);

    //POST https://api.openai.com/v1/completions

    @PostExchange(url = "/v1/completions")
    Mono<CompletionResponse> completions(@RequestBody CompletionParam param);

    /**
     * POST https://api.openai.com/v1/chat/completions
     * Creates a completion for the chat message
     */
    @PostExchange(url = "/v1/chat/completions")
    Flux<ChatResponse> chatCompletions(@RequestBody ChatCompletionParam param);
}
