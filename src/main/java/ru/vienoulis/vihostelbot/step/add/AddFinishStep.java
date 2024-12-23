package ru.vienoulis.vihostelbot.step.add;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.dto.Visitor;
import ru.vienoulis.vihostelbot.repo.Repository;
import ru.vienoulis.vihostelbot.step.AbstractFinishStep;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddFinishStep extends AbstractFinishStep {

    private final Repository<Visitor> visitorRepository;

    @Override
    public String getMessage() {
        log.info("getMessage; save temp visitor in repo");
        var transitionalEntry = visitorRepository.getTransitionalEntry();
        visitorRepository.saveEntry(transitionalEntry);
        return "Заселение завершено. Посетитель: %s".formatted(transitionalEntry);
    }
}
