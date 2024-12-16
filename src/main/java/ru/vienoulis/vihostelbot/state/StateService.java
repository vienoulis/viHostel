package ru.vienoulis.vihostelbot.state;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vienoulis.vihostelbot.dto.Action;

@Service
public class StateService {

    @Getter
    private State currentState = State.INIT;
    @Getter
    private State previousState = State.INIT;

    public void ready() {
        setCurrentState(State.READY);
    }

    public void error() {
        setCurrentState(State.ERROR);
    }

    public boolean isProcessStarted() {
        return currentState == State.IN_PROCESS;
    }

    private void setCurrentState(State state) {
        previousState = currentState;
        currentState = state;
    }

    public void process(Action action) {
        previousState = currentState;
        currentState = State.IN_PROCESS;
        currentState.setProcess(action);
    }
}
