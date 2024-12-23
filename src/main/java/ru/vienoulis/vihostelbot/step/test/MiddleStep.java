package ru.vienoulis.vihostelbot.step.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.state.StateService;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MiddleStep implements Step {

    private final StateService stateService;
    @Override
    public String getMessage() {
        log.info("getMessage; second test step; stateService: {}", stateService.getCurrentState());
        return "Добро пожаловать на следующий тестовый шаг. Для продолжение отгадай о чем я сейчас думаю?";
    }

    @Override
    public boolean tryApply(Message response) {
        log.info("tryApply; return always true;");
        return true;
    }

    @Override
    public String onFail(Message response) {
        throw new UnsupportedOperationException("Can apply always true");
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}
