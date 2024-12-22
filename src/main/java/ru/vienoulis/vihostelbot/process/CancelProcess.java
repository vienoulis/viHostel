package ru.vienoulis.vihostelbot.process;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.state.StateService;

import static ru.vienoulis.vihostelbot.dto.Action.CANCEL;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CancelProcess extends AbstractProcess {

    private final StateService stateService;

    @Override
    public Action getAction() {
        return CANCEL;
    }

    @Override
    public Optional<SendMessage> onMessage(Message message) {
        log.info("onMessage;");
        return stateService.getCurrentProcessIfExist()
                .flatMap(p -> p.messageOnCancel(message))
                .or(() -> Optional.of(SendMessage.builder()
                        .text("Нет процесса, который можно отменить")
                        .chatId(message.getChatId())
                        .build()));
    }

    @Override
    public Optional<SendMessage> messageOnCancel(Message message) {
        log.info("messageOnCancel; no-op");
        return Optional.empty();
    }
}
