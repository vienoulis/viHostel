package ru.vienoulis.vihostelbot.process;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.state.StateService;

import static ru.vienoulis.vihostelbot.dto.Action.CANCEL;

@Slf4j
@Component
public class CancelProcess extends AbstractProcess {

    public CancelProcess(StateService stateService) {
        super(stateService);
    }

    @Override
    public Action getAction() {
        return CANCEL;
    }

    @Override
    public Optional<SendMessage> processAndGetMessage(Message message) {
        log.info("onMessage;");
        return stateService.getCurrentProcessIfExist()
                .flatMap(p -> p.messageOnCancel(message))
                .or(() -> Optional.of(SendMessage.builder()
                        .text("Нет процесса, который можно отменить")
                        .chatId(message.getChatId())))
                .map(SendMessageBuilder::build);
    }

    @Override
    protected void onProcessStart() {
       log.info("onProcessStart; no-op");
    }

    @Override
    protected boolean isProcessFinish() {
        return true;
    }

    @Override
    protected void onProcessFinish() {
       log.info("onProcessFinish; no-op");
    }

    @Override
    protected Optional<SendMessageBuilder> onMessage(Message message) {
        return stateService.getCurrentProcessIfExist()
                .flatMap(p -> p.messageOnCancel(message))
                .or(() -> Optional.of(SendMessage.builder()
                        .text("Нет процесса, который можно отменить")));
    }

    @Override
    public Optional<SendMessageBuilder> messageOnCancel(Message message) {
        log.info("messageOnCancel; no-op");
        return Optional.empty();
    }
}
