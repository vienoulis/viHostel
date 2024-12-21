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

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddVisitorProcess implements Process {

    private final StateService stateService;

    @Override
    public void start() {
        log.info("start.enter;");
        stateService.process(this);
        log.info("start.exit;");
    }

    @Override
    public Optional<SendMessage> onMessage(Message message) {
        log.info("onMessage;");
        var sendMessage = new SendMessage();
        sendMessage.setText("ФИО на заселение");
        sendMessage.setChatId(message.getChatId());
        return Optional.of(sendMessage);
    }

    @Override
    public Action getAction() {
        return Action.ADD;
    }
}
