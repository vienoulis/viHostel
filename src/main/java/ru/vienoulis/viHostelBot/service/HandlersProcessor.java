package ru.vienoulis.viHostelBot.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.callback.AbstractCallbackHandler;
import ru.vienoulis.viHostelBot.handler.message.AbstractMessageHandler;

@Slf4j
@Service
public class HandlersProcessor {

    private final Set<AbstractMessageHandler> messageHandlers = new HashSet<>();
    private final Set<AbstractCallbackHandler> callbackHandlers = new HashSet<>();

    public Collection<AbstractMessageHandler> applyMessageHandlers(Message message) {
        return messageHandlers.stream()
                .filter(h -> message.getText().matches(h.regex()))
                .filter(h -> h.validate(message))
                .collect(Collectors.toSet());
    }

    public Collection<AbstractCallbackHandler> applyMessageHandlers(CallbackQuery callbackQuery) {
        return callbackHandlers.stream()
                .filter(h -> callbackQuery.getData().matches(h.regex()))
                .filter(h -> h.validate(callbackQuery))
                .collect(Collectors.toSet());
    }

    public void registerHandler(AbstractMessageHandler handler) {
        messageHandlers.add(handler);
        log.info("registerHandler; handler: {} registered;", handler.getClass().getSimpleName());
    }

    public void registerHandler(AbstractCallbackHandler handler) {
        callbackHandlers.add(handler);
        log.info("registerHandler; handler: {} registered;", handler.getClass().getSimpleName());
    }

}
