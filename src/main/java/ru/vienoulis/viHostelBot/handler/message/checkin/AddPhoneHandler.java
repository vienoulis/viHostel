package ru.vienoulis.viHostelBot.handler.message.checkin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.vienoulis.viHostelBot.handler.message.AbstractMessageHandler;
import ru.vienoulis.viHostelBot.service.CheckInService;
import ru.vienoulis.viHostelBot.state.State;
import ru.vienoulis.viHostelBot.state.State.CheckInSubState;

import static ru.vienoulis.viHostelBot.model.CallBack.PAYMENT_DONE;
import static ru.vienoulis.viHostelBot.model.CallBack.PAYMENT_REJECT;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddPhoneHandler extends AbstractMessageHandler {

    private final CheckInService checkInService;

    @Override
    public String regex() {
        return ".*.";
    }

    @Override
    public void enrich(Message receivedMsg, SendMessageBuilder msgToSend) {
        log.info("enrich.enter;");
        checkInService.setPhone(receivedMsg.getText());
        msgToSend.text("Оплатите посещение:");
        msgToSend.replyMarkup(InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(PAYMENT_DONE.createBtn(), PAYMENT_REJECT.createBtn()))
                .build());
        stateMachine.currentState().setSubState(CheckInSubState.NEED_PAY);
        log.info("enrich.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() == State.CHECK_IN
                && stateMachine.currentState().getSubState() == CheckInSubState.NEED_PHONE;
    }
}
