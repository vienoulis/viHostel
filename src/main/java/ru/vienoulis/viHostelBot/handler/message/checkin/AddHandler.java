package ru.vienoulis.viHostelBot.handler.checkin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.message.AbstractMessageHandler;
import ru.vienoulis.viHostelBot.service.CheckInService;
import ru.vienoulis.viHostelBot.state.State;
import ru.vienoulis.viHostelBot.state.State.CheckInSubState;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddHandler extends AbstractMessageHandler {

    private final CheckInService checkInService;

    @Override
    public String regex() {
        return "/add";
    }

    @Override
    public void enrich(Message receivedMsg, SendMessageBuilder msgToSend) {
        log.info("process.enter;");
        stateMachine.transitTo(State.CHECK_IN);
        stateMachine.currentState().setSubState(CheckInSubState.NEED_NAME);
        checkInService.start();
        msgToSend.text("Введите ФИО посетителя");
        log.info("process.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() != State.CHECK_IN;
    }
}
