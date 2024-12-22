package ru.vienoulis.vihostelbot.step.add;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddFinishStep implements Step {

    private static final String REGEX = "-?\\d+(\\.\\d+)?";

    @Override
    public String processMessage(Message message) {
        return "Заселение завершено";
    }

    @Override
    public boolean canApplied(Message message) {
        return message.getText().matches(REGEX);
    }
}
