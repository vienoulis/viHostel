package ru.vienoulis.vihostelbot.step.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MiddleStep implements Step {

    @Override
    public String processMessage(Message message) {
        log.info("processMessage;");
        return "Добро пожаловать на следующий тестовый шаг. Для продолжение отгадай о чем я сейчас думаю?";
    }


    @Override
    public boolean canApplied(Message message) {
        return StringUtils.equals(message.getText(), "123");
    }
}
