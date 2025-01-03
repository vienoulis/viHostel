package ru.vienoulis.vihostelbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-kuzmich.properties")
public class ViHostelBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViHostelBotApplication.class, args);
    }
}
