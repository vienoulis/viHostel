package ru.vienoulis.vihostelbot.step.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartStep implements Step {

    @Override
    public String processMessage(Message message) {
        log.info("processMessage;");
        return "Начинаю тестовый процесс. Для перехода на следующий шаг введите: '123'";
    }

    @Override
    public boolean canApplied(Message message) {
        return true;
    }
}
