package ru.vienoulis.vihostelbot.state;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Getter
    private State currentState = State.INIT;
    @Getter
    private State previousState = State.INIT;

    public void started() {
        setCurrentState(State.STARTED);
    }

    public void error() {
        setCurrentState(State.ERROR);
    }

    private void setCurrentState(State state) {
        previousState = currentState;
        currentState = state;
    }

}
