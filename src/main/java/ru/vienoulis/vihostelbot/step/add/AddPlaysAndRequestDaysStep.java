package ru.vienoulis.vihostelbot.step.add;

import java.time.LocalDate;
import java.time.Period;
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
public class AddPlaysAndRequestDaysStep implements Step {

    private static final String REGEX = "-?\\d+(\\.\\d+)?";

    private final Repository<Visitor> visitorRepository;

    @Override
    public String processMessage(Message message) {
        log.info("processMessage;");
        var visitor = visitorRepository.getTransitionalEntry().toBuilder()
                .placeInRoom(Integer.parseInt(message.getText()))
                .build();
        visitorRepository.setTransitionalEntry(visitor);
        return "На сколько дней:";
    }

    @Override
    public boolean canApplied(Message message) {
        return message.getText().matches(REGEX);
    }
}
