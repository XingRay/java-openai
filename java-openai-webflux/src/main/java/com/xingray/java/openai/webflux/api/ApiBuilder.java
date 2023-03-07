package com.xingray.java.openai.webflux.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;
import java.time.Duration;


public class ApiBuilder {

    private final HttpServiceProxyFactory factory;

    public ApiBuilder(String apiKey, String proxyHost, int proxyPort, ProxyProvider.Proxy proxyType) {
        String baseUrl = "https://api.openai.com";
        WebClient client = WebClient.builder()
                .exchangeStrategies(exchangeStrategies(objectMapper()))
                .baseUrl(baseUrl)
                .clientConnector(clientHttpConnector(proxyHost, proxyPort, proxyType))
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Accept", "text/event-stream")
                .defaultHeader("Content-Type", "application/json")
                .build();
        factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    private ExchangeStrategies exchangeStrategies(ObjectMapper objectMapper) {
        return ExchangeStrategies.builder()
                .codecs(configurer -> {
                    ClientCodecConfigurer.ClientDefaultCodecs codecs = configurer.defaultCodecs();
                    codecs.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    codecs.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                })
                .build();
    }

    private ClientHttpConnector clientHttpConnector(String host, int port, ProxyProvider.Proxy proxyType) {
        HttpClient httpClient = HttpClient.create()
                .proxy(proxy -> proxy.type(proxyType).address(new InetSocketAddress(host, port)))
                .responseTimeout(Duration.ofSeconds(30))
                .secure(ssl -> ssl.sslContext(getSslContext()));

        return new ReactorClientHttpConnector(httpClient);
    }

    private SslContext getSslContext() {
        try {
            return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OpenaiApi openaiApi() {
        return factory.createClient(OpenaiApi.class);
    }
}
