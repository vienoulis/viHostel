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

    private static final String PAY_DAY_NOW = "Оплата сегодня:";
    private static final String PAY_DAY_EARLIE = "Просрочка 1 день и больше:";
    private static final String DELIMITER = ",\n";
    private static final StringJoiner PAY_DAY_NOW_SJ = new StringJoiner(DELIMITER);
    private static final StringJoiner PAY_DAY_EARLIE_SJ = new StringJoiner(DELIMITER);
    private final Repository<SheetsVisitor> sheetsVisitorRepository;

    @Override
    public String getMessage() {
        log.info("getMessage;");
        var now = LocalDate.now();
        var oweVisitors = sheetsVisitorRepository.getEntrysBy(this::needRepay);
        oweVisitors.stream()
                .filter(sv -> sv.getEndDate().isEqual(now))
                .map(SheetsVisitor::toString)
                .forEach(PAY_DAY_NOW_SJ::add);
        oweVisitors.stream()
                .filter(sv -> sv.getEndDate().isBefore(now))
                .map(SheetsVisitor::toString)
                .forEach(PAY_DAY_EARLIE_SJ::add);

        return "%s\n %s\n\n\n %s\n %s".formatted(PAY_DAY_NOW, PAY_DAY_NOW_SJ.toString(),
                PAY_DAY_EARLIE, PAY_DAY_EARLIE_SJ.toString());
    }

    private boolean needRepay(SheetsVisitor sheetsVisitor) {
        var now = LocalDate.now();
        var endDate = sheetsVisitor.getEndDate();
        return endDate.isEqual(now) || endDate.isBefore(now);
    }

}
