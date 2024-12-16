package ru.vienoulis.vihostelbot.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        stateService.process(getAction());
        log.info("start.exit;");
    }

    @Override
    public void onMessage(Message message) {
        log.info("onMessage.enter;");
        log.info("onMessage.exit;");
    }

    @Override
    public Action getAction() {
        return Action.ADD;
    }
}
