package ru.vienoulis.viHostelBot.handler.message.checkin;

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
public class AddNameHandler extends AbstractMessageHandler {

    private final CheckInService checkInService;

    @Override
    public String regex() {
        return "^[А-Яа-я]+\\s[А-Яа-я]+\\s?[А-Яа-я]*\\s?$";
    }

    @Override
    public void enrich(Message receivedMsg, SendMessageBuilder msgToSend) {
        log.info("enrich.enter;");
        checkInService.setName(receivedMsg.getText());
        msgToSend.text("Введите номер комнаты:");
        stateMachine.currentState().setSubState(CheckInSubState.NEED_ROOM);
        log.info("enrich.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() == State.CHECK_IN
                && stateMachine.currentState().getSubState() == CheckInSubState.NEED_NAME;
    }
}
