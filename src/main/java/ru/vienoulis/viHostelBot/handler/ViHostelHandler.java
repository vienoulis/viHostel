package ru.vienoulis.viHostelBot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.service.HandlersProcessor;

@Slf4j
@Service
public abstract class ViHostelHandler {

    @Autowired
    private HandlersProcessor handlersProcessor;

    public abstract String action();

    public abstract void process(Message message);

    public abstract boolean validate(Message message);

    protected void registerHandlers(ViHostelHandler handler) {
        handlersProcessor.registerHandler(handler);
    }
}
