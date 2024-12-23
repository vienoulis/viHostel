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
public class RequestDaysStep implements Step {

    private static final String REGEX = "-?\\d+(\\.\\d+)?";

    private final Repository<Visitor> visitorRepository;

    @Override
    public String getMessage() {
        log.info("getMessage;");
        return "Введите количество дней прибывания:";
    }

    @Override
    public boolean tryApply(Message response) {
        log.info("tryApply.enter;");
        var text = response.getText();
        if (!text.matches(REGEX)) {
            log.info("tryApply.exit; text: {} not supported", text);
            return false;
        }
        var paidBefore = LocalDate.now().plus(Period.ofDays(Integer.parseInt(text)));
        visitorRepository.setTransitionalEntry(visitorRepository.getTransitionalEntry().toBuilder()
                .paidBefore(paidBefore)
                .build());
        log.info("tryApply.exit; save paidBefore: {} in temp user", paidBefore);
        return true;
    }

    @Override
    public String onFail(Message response) {
        return "Введите число дней прибывания;";
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}
