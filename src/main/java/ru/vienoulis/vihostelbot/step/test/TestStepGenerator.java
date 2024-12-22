package ru.vienoulis.vihostelbot.step.test;

import java.util.LinkedList;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.step.Step;
import ru.vienoulis.vihostelbot.step.StepGenerator;

@Component
public class TestStepGenerator implements StepGenerator {

    private final Queue<Step> steps;

    @Autowired
    public TestStepGenerator(StartStep startStep, MiddleStep middleStep, FinalStep finalStep) {
        steps = new LinkedList<>();
        steps.add(startStep);
        steps.add(middleStep);
        steps.add(finalStep);
    }

    @Override
    public Queue<Step> getStep() {
        return steps;
    }

    @Override
    public Action getAction() {
        return Action.TEST;
    }
}
