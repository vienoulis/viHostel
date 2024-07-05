package ru.vienoulis.viHostelBot.handler.checkin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.ViHostelHandler;
import ru.vienoulis.viHostelBot.state.State;

@Slf4j
@Service
public class AddInProcessErrorHandler extends ViHostelHandler {

    @Override
    public String regex() {
        return "/add";
    }

    @Override
    public void enrich(SendMessageBuilder message) {
        log.info("process.enter;");
        message.text("Завершите предыдущее добавление");
        log.info("process.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() == State.CHECK_IN;
    }
}
