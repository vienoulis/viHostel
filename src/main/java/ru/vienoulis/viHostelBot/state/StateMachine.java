package ru.vienoulis.viHostelBot.state;

import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static ru.vienoulis.viHostelBot.state.State.IDLE;

@Slf4j
@Service
public class StateMachine {

    private final AtomicReference<State> currentState = new AtomicReference<>(IDLE);
    private final AtomicReference<State> previousState = new AtomicReference<>(currentState.get());

    public State currentState() {
        return currentState.get();
    }

    public State previousState() {
        return previousState.get();
    }

    public void transitTo(State states) {
        log.info("transitTo.enter; old state: {}", currentState.get());
        previousState.set(currentState.get());
        currentState.set(states);
        log.info("transitTo.enter; new state: {}", currentState.get());
    }
}
