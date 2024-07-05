package ru.vienoulis.viHostelBot.handler.checkin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.ViHostelHandler;
import ru.vienoulis.viHostelBot.state.State;
import ru.vienoulis.viHostelBot.state.State.CheckInSubState;

@Slf4j
@Service
public class AddHandler extends ViHostelHandler {

    @Override
    public String regex() {
        return "/add";
    }

    @Override
    public void enrich(SendMessageBuilder message) {
        log.info("process.enter;");
        stateMachine.transitTo(State.CHECK_IN);
        stateMachine.currentState().setSubState(CheckInSubState.NEED_NAME);
        message.text("Введите ФИО посетителя");
        log.info("process.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() != State.CHECK_IN;
    }
}
