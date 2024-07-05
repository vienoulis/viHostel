package ru.vienoulis.viHostelBot.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.ViHostelHandler;

@Slf4j
@Service
public class HandlersProcessor {

    private final Set<ViHostelHandler> handlers = new HashSet<>();

    public Collection<ViHostelHandler> getAppliebleHandlers(Message message) {
        return handlers.stream()
                .filter(h -> message.getText().matches(h.regex()))
                .filter(h -> h.validate(message))
                .collect(Collectors.toSet());
    }

    public void registerHandler(ViHostelHandler handler) {
        handlers.add(handler);
        log.info("registerHandler; handler: {} registered;", handler.getClass().getSimpleName());
    }

}
