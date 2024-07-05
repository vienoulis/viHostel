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
public class AddPhoneHandler extends ViHostelHandler {

    @Override
    public String regex() {
        return ".*.";
    }

    @Override
    public void enrich(SendMessageBuilder message) {
        log.info("enrich.enter;");
        message.text("Оплатите посещение:");
        stateMachine.currentState().setSubState(CheckInSubState.NEED_PAY);
        log.info("enrich.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() == State.CHECK_IN
                && stateMachine.currentState().getSubState() == CheckInSubState.NEED_PHONE;
    }
}
