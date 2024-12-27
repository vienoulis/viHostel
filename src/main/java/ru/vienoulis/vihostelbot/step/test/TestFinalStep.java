package ru.vienoulis.vihostelbot.step.test;

import java.time.LocalDate;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.dto.SheetsVisitor;
import ru.vienoulis.vihostelbot.repo.Repository;
import ru.vienoulis.vihostelbot.step.AbstractFinishStep;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestFinalStep extends AbstractFinishStep {

    private final Repository<SheetsVisitor> sheetsVisitorRepository;

    @Override
    public String getMessage() {
        log.info("getMessage;");
        var now = LocalDate.now();

        StringJoiner joiner = new StringJoiner(", \n");
        sheetsVisitorRepository.getEntrysBy(sv -> true)
                .forEach(sv -> joiner.add(sv.toString()));
        return joiner.toString();
    }
}
