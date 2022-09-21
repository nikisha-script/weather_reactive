package ru.job4j.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.job4j.reactive.weather.HtmlParser;

@Configuration
public class Config {

    @Bean
    public HtmlParser htmlParser() {
        return new HtmlParser();
    }

}
