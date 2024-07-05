package ru.vienoulis.viHostelBot.handler;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.service.HandlersProcessor;
import ru.vienoulis.viHostelBot.state.StateMachine;

@Slf4j
@Service
public abstract class ViHostelHandler {

    @Autowired
    private HandlersProcessor handlersProcessor;

    @Autowired
    protected StateMachine stateMachine;

    public abstract String regex();

    public abstract void enrich(Message receivedMsg, SendMessageBuilder msgToSend);

    public abstract boolean validate(Message message);

    @PostConstruct
    private void registerHandlers() {
        handlersProcessor.registerHandler(this);
    }
}
