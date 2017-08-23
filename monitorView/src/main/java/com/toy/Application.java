package com.toy;

import io.undertow.UndertowOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on
 * 2017/8/23.
 */
@EnableAutoConfiguration
@Configuration
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.addBuilderCustomizers(
                (UndertowBuilderCustomizer) builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
        );
        return factory;
    }

    @RestController
    @RequestMapping("/test")
    public class HiController {
        @RequestMapping("/hello")
        public String hello() {
            return "world";
        }
    }

}
