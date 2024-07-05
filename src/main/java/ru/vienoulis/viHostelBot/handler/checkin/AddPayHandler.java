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
public class AddPayHandler extends ViHostelHandler {

    @Override
    public String regex() {
        return ".*.";
    }

    @Override
    public void enrich(Message receivedMsg, SendMessageBuilder msgToSend) {
        log.info("enrich.enter;");
        log.info("enrich.exit;");
    }

    @Override
    public boolean validate(Message message) {
        var currentState = stateMachine.currentState();
        return currentState == State.CHECK_IN && currentState.getSubState() == CheckInSubState.NEED_PAY;
    }
}
