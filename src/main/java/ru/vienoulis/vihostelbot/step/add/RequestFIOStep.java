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
public class RequestFIOStep implements Step {

    private final Repository<Visitor> visitorRepository;

    private static final String REGEX = "^[А-ЯЁ][а-яё]+\\s[А-ЯЁ][а-яё]+\\s[А-ЯЁ][а-яё]+$";

    @Override
    public String getMessage() {
        log.info("getMessage; set empty temp visitor");
        visitorRepository.setTransitionalEntry(Visitor.builder().build());
        return "Начинаем заселение.\nВведите ФИО";
    }

    @Override
    public boolean tryApply(Message response) {
        log.info("tryApply.enter;");
        var text = response.getText();
        if (!text.matches(REGEX)) {
            log.info("tryApply.exit; not FIO matches. text: {}", text);
            return false;
        }

        visitorRepository.setTransitionalEntry(visitorRepository.getTransitionalEntry().toBuilder()
                .name(text)
                .build());
        log.info("tryApply.exit; save FIO: {} in temp user", text);
        return true;
    }

    @Override
    public String onFail(Message response) {
        log.info("onFail;");
        var text = response.getText();
        return "%s не похоже на Фамилию Имя Отчество, пожалуйста введите все с большой буквы.".formatted(text);
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}
