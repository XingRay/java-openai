package com.xingray.java.openai.webflux.api;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;


public class ApiBuilder {

    private final HttpServiceProxyFactory factory;

    public ApiBuilder(String apiKey, String proxyHost, int proxyPort, ProxyProvider.Proxy proxyType) {
        String baseUrl = "https://api.openai.com";
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(clientHttpConnector(proxyHost, proxyPort, proxyType))
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Accept", "text/event-stream")
                .defaultHeader("Content-Type", "application/json")
                .build();
        factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
    }

    public ClientHttpConnector clientHttpConnector(String host, int port, ProxyProvider.Proxy proxyType) {
        HttpClient httpClient = HttpClient.create().tcpConfiguration(
                        tcpClient -> tcpClient.proxy(
                                proxy -> proxy.type(proxyType)
                                        .address(new InetSocketAddress(host, port))))
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
