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
    private AtomicReference<Process> currentProcess = new AtomicReference<>();

    public void ready() {
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
            return Optional.ofNullable(currentProcess.get());
        }
        return Optional.empty();
    }

    private void setCurrentState(State state) {
        previousState = currentState;
        currentState = state;
    }

    public void process(Process process) {
        log.info("process.enter; previous state: {}", currentState);
        previousState = currentState;
        currentState = State.IN_PROCESS;
        currentProcess.set(process);
        log.info("process.enter; current process: {}", currentProcess.getClass().getName());
    }
}
