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
public class FinalStep implements Step {

    @Override
    public String processMessage(Message message) {
        log.info("processMessage;");
        return "Как ты догадался? Я как раз и думал о '%s'. Тестовый процесс завершен. Спасибо."
                .formatted(message.getText());
    }

    @Override
    public boolean canApplied(Message message) {
        return true;
    }
}
