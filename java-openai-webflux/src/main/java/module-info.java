module com.xingray.java.openai.webflux {
    requires spring.webflux;
    requires spring.web;
    requires spring.core;

    requires reactor.core;
    requires reactor.netty;
    requires reactor.netty.http;
    requires reactor.netty.core;

    requires io.netty.handler;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports com.xingray.java.openai.webflux.entity;
    exports com.xingray.java.openai.webflux.api;
}