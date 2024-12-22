package ru.vienoulis.vihostelbot.step.add;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Visitor;
import ru.vienoulis.vihostelbot.repo.Repository;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddPhoneAndFinishStep implements Step {

    private final Repository<Visitor> visitorRepository;
    private static final String REGEX = "^(\\+79|89)\\d{9}$";

    @Override
    public String processMessage(Message message) {
        var visitor = visitorRepository.getTransitionalEntry().toBuilder()
                .phone(message.getText())
                .build();
        visitorRepository.saveEntry(visitor);
        return "Заселение завершено, посетитель: '%s'".formatted(visitor);
    }

    @Override
    public boolean canApplied(Message message) {
        return message.getText().matches(REGEX);
    }
}
