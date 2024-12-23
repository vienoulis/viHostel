package ru.vienoulis.vihostelbot.state;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vienoulis.vihostelbot.process.Process;

@Service
@Slf4j
public class StateService {

    @Getter
    private State currentState = State.INIT;
    @Getter
    private State previousState = State.INIT;
    private final AtomicReference<Process> currentProcess = new AtomicReference<>();

    public void ready() {
        currentProcess.set(null);
        setCurrentState(State.READY);
    }

    public void error() {
        setCurrentState(State.ERROR);
    }

    public boolean isProcessStarted() {
        return currentState == State.IN_PROCESS;
    }

    public Optional<Process> getCurrentProcessIfExist() {
        if (currentState == State.IN_PROCESS) {
            log.debug("getCurrentProcessIfExist; return: {}", currentProcess.get());
            return Optional.ofNullable(currentProcess.get());
        }
        return Optional.empty();
    }

    private void setCurrentState(State state) {
        log.info("setCurrentState.enter; before: {}", currentState);
        previousState = currentState;
        currentState = state;
        log.info("setCurrentState.exit; after: {}", currentState);
    }

    public void process(Process process) {
        log.info("process.enter; previous state: {}", currentState);
        previousState = currentState;
        currentState = State.IN_PROCESS;
        currentProcess.set(process);
        log.info("process.enter; process action: {}", currentProcess.get().getAction());
    }
}
