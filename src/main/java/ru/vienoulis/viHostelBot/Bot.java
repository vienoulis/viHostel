package ru.vienoulis.viHostelBot;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
public class Bot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;

    @Autowired
    public Bot() {
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: {}", this.getBotToken());
    }
}
