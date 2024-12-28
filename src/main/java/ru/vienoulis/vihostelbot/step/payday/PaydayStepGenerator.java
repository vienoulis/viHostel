package ru.vienoulis.vihostelbot.step.payday;

import java.util.LinkedList;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.step.Step;
import ru.vienoulis.vihostelbot.step.StepGenerator;

@Component
public class PaydayStepGenerator implements StepGenerator {

    private final Queue<Step> steps;

    @Autowired
    public PaydayStepGenerator(PaydaySingleStep paydaySingleStep) {
        steps = new LinkedList<>();
        steps.add(paydaySingleStep);
    }

    @Override
    public Queue<Step> getStep() {
        return steps;
    }

    @Override
    public Action getAction() {
        return Action.PAYDAY;
    }
}
