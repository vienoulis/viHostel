package ru.vienoulis.vihostelbot.step.add;

import jakarta.annotation.PostConstruct;
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

    private final Queue<Step> steps = new LinkedList<>();

    private final StartAddStep startAddStep;
    private final FIOAddedStep fioAddedStep;
    private final AddFinishStep addFinishStep;

    @PostConstruct
    public void postConstruct() {
        steps.add(startAddStep);
        steps.add(fioAddedStep);
        steps.add(addFinishStep);
    }

    @Override
    public Queue<Step> getStep() {
        return steps;
    }

    @Override
    public String onCancelMessage() {
        return "Процесс заселения прерван.";
    }

    @Override
    public Action getAction() {
        return Action.ADD;
    }
}
