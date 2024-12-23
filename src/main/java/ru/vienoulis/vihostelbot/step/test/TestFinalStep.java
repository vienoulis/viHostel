package ru.vienoulis.vihostelbot.step.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.step.AbstractFinishStep;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestFinalStep extends AbstractFinishStep {

    @Override
    public String getMessage() {
        log.info("getMessage;");
        return "Все верно это заключительный шаг. До свидание";
    }
}
