package ru.vienoulis.vihostelbot.step.list;

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
public class AllStepGenerator implements StepGenerator {

    private final Queue<Step> steps = new LinkedList<>();

    private final AllSingleStep allSingleStep;

    @PostConstruct
    public void postConstruct() {
        steps.add(allSingleStep);
    }

    @Override
    public Queue<Step> getStep() {
        return steps;
    }

    @Override
    public Action getAction() {
        return Action.ALL;
    }
}
