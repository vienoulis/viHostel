package ru.vienoulis.vihostelbot.step.add;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Visitor;
import ru.vienoulis.vihostelbot.repo.Repository;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestPhoneStep implements Step {

    private static final String NO_PHONE_TEXT = "Нет";

    private static final String REGEX = "-?\\d+(\\.\\d+)?";

    private final Repository<Visitor> visitorRepository;

    @Override
    public String getMessage() {
        log.info("getMessage;");
        return "Введите телефон посетителя начиная с 89... или +79... Введите слово %s если телефон неизвестен.".formatted(
                NO_PHONE_TEXT);
    }

    @Override
    public boolean tryApply(Message response) {
        log.info("tryApply.enter;");
        var text = response.getText();
        if (StringUtils.endsWithIgnoreCase(text, NO_PHONE_TEXT) || text.matches(REGEX)) {
            visitorRepository.setTransitionalEntry(visitorRepository.getTransitionalEntry().toBuilder()
                    .phone(text)
                    .build());
            log.info("tryApply.exit; save phone: {} in temp user", text);
            return true;
        }
        log.info("tryApply.exit; text: {} not match phone regexp", text);
        return false;
    }

    @Override
    public String onFail(Message response) {
        log.info("onFail;");
        return response.getText() + " не телефон";
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}
