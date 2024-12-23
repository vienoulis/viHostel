package ru.vienoulis.vihostelbot.step.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.state.StateService;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartStep implements Step {

    private final StateService stateService;

    @Override
    public String getMessage() {
        log.info("getMessage;");
        return "Для перехода на следующий шаг введите: '123'";
    }

    @Override
    public boolean tryApply(Message response) {
        var text = response.getText();
        var result = StringUtils.equals(text, "123");
        log.info("tryApply; stateService: {}, text: {}, result: {}", stateService.getCurrentState(), text, result);
        return result;
    }

    @Override
    public String onFail(Message response) {
        log.info("onFail;");
        return "Ожидаю число '123'";
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}
