package ru.vienoulis.viHostelBot.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.ViHostelHandler;

@Slf4j
@Service
public class HandlersProcessor {

    private final Set<ViHostelHandler> handlers = new HashSet<>();

    public void processMessage(Message message) {
        handlers.stream()
                .filter(h -> StringUtils.equals(message.getText(), h.action()))
                .filter(h -> h.validate(message))
                .collect(Collectors.toSet())
                .forEach(h -> h.process(message));
    }

    public void registerHandler(ViHostelHandler handler) {
        handlers.add(handler);
        log.info("registerHandler; handler: {} registered;", handler.getClass().getSimpleName());
    }

}
