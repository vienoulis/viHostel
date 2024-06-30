package ru.vienoulis.viHostelBot.handler;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Service
public class AddHandler extends ViHostelHandler {

    @PostConstruct
    private void register() {
        log.info("register;");
        registerHandlers(this);
    }

    @Override
    public String action() {
        return "/add";
    }

    @Override
    public void process(Message message) {
        log.info("process.enter;");
    }

    @Override
    public boolean validate(Message message) {
        return true;
    }
}
