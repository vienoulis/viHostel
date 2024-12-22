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
public class StartAndRequestFIOStep implements Step {

    private final Repository<Visitor> visitorRepository;

    @Override
    public String processMessage(Message message) {
        log.info("processMessage;");
        visitorRepository.setTransitionalEntry(Visitor.builder().build());
        return "Начинаем заселение.\nВведите ФИО";
    }

    @Override
    public boolean canApplied(Message message) {
        return true;
    }
}
