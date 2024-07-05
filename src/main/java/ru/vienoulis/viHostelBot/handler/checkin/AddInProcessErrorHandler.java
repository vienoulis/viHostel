package ru.vienoulis.viHostelBot.handler.checkin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.handler.ViHostelHandler;
import ru.vienoulis.viHostelBot.service.CheckInService;
import ru.vienoulis.viHostelBot.state.State;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddInProcessErrorHandler extends ViHostelHandler {

    @Override
    public String regex() {
        return "/add";
    }

    @Override
    public void enrich(Message receivedMsg, SendMessageBuilder msgToSend) {
        log.info("process.enter;");
        msgToSend.text("Завершите предыдущее добавление");
        log.info("process.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() == State.CHECK_IN;
    }
}
