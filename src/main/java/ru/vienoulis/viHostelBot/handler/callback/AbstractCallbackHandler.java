package ru.vienoulis.viHostelBot.handler.callback;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.vienoulis.viHostelBot.model.CallBack;
import ru.vienoulis.viHostelBot.service.HandlersProcessor;
import ru.vienoulis.viHostelBot.state.StateMachine;

@Slf4j
@Service
public abstract class AbstractCallbackHandler {

    @Autowired
    protected HandlersProcessor handlersProcessor;

    @Autowired
    protected StateMachine stateMachine;

    protected abstract CallBack callBack();

    public abstract void enrich(CallbackQuery callbackQuery, SendMessageBuilder msgToSend);

    public abstract boolean validate(CallbackQuery callbackQuery);

    public String callBackData() {
        return callBack().name();
    }

    @PostConstruct
    private void registerHandlers() {
        handlersProcessor.registerHandler(this);
    }

}
