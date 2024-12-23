package ru.vienoulis.vihostelbot.step.add;

import jakarta.annotation.PostConstruct;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.step.Step;
import ru.vienoulis.vihostelbot.step.StepGenerator;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddStepGenerator implements StepGenerator {

    private final Deque<Step> steps = new LinkedList<>();

    private final RequestFIOStep requestFIOStep; // введите имя
    private final RequestDaysStep requestDaysStep; // введите количество дней
    private final RequestPlaysStep requestPlaysStep;
    private final RequestPhoneStep addDaysAndRequestPhoneStep;
    private final AddFinishStep addFinishStep;

    @PostConstruct
    public void postConstruct() {
        steps.add(requestFIOStep);
        steps.add(requestDaysStep);
        steps.add(requestPlaysStep);
        steps.add(addDaysAndRequestPhoneStep);
        steps.add(addFinishStep);
    }

    @Override
    public Queue<Step> getStep() {
        return steps;
    }

    @Override
    public Action getAction() {
        return Action.ADD;
    }
}
