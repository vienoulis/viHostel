package ru.vienoulis.viHostelBot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.viHostelBot.state.State;

@Slf4j
@Service
public class AddHandler extends ViHostelHandler {

    @Override
    public String action() {
        return "/add";
    }

    @Override
    public void process(Message message) {
        log.info("process.enter;");
        stateMachine.transitTo(State.CHECK_IN);
        log.info("process.exit;");
    }

    @Override
    public boolean validate(Message message) {
        return stateMachine.currentState() != State.CHECK_IN;
    }
}
