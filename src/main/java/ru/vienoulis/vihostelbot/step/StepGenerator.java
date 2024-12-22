package ru.vienoulis.vihostelbot.step;

import java.util.Queue;
import ru.vienoulis.vihostelbot.dto.Action;

public interface StepGenerator {

    Queue<Step> getStep();

    String onCancelMessage();

    Action getAction();
}
