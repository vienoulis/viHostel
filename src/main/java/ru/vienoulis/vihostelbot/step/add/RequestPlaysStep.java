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
public class RequestPlaysStep implements Step {

    private static final String REGEX = "-?\\d+(\\.\\d+)?";

    private final Repository<Visitor> visitorRepository;


    @Override
    public String getMessage() {
        log.info("getMessage;");
        return "Введите номер места:";
    }

    @Override
    public boolean tryApply(Message response) {
        log.info("tryApply.enter;");
        var text = response.getText();
        if (!text.matches(REGEX)) {
            log.info("tryApply.exit; text: {} not match regexp", text);
            return false;
        }
        visitorRepository.setTransitionalEntry(visitorRepository.getTransitionalEntry().toBuilder()
                .placeInRoom(Integer.parseInt(text))
                .build());
        log.info("tryApply.exit; save placeInRoom: {} in temp visitor", text);
        return true;
    }

    @Override
    public String onFail(Message response) {
        log.info("onFail;");
        return "Введите число;";
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}
