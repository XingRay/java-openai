module com.xingray.java.openai.webflux.test {
    requires com.xingray.java.openai.webflux;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires org.junit.jupiter.api;
    requires reactor.netty.core;
    requires reactor.netty.http;
    requires reactor.core;
    requires org.reactivestreams;

    opens com.xingray.java.openai.webflux.test;
}