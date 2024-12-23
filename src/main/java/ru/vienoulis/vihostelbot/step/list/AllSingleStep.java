package ru.vienoulis.vihostelbot.step.list;

import java.util.StringJoiner;
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
public class AllSingleStep extends AbstractFinishStep {

    private final Repository<Visitor> visitorRepository;

    @Override
    public String getMessage() {
        log.info("getMessage; send all visitor in repo");
        StringJoiner joiner = new StringJoiner(", \n ");
        visitorRepository.getEntrysBy(v -> true).forEach(v -> joiner.add(v.toString()));
        return "Посетители: \n %s".formatted(joiner.toString());
    }
}
