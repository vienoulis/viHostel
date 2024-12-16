package ru.vienoulis.vihostelbot.state;

import java.util.Objects;
import ru.vienoulis.vihostelbot.dto.Action;

public enum State {
    INIT,
    READY,
    IN_PROCESS,
    ERROR;

    private Action action;

    public Action getProcess() {
        return action;
    }

    public void setProcess(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        if (Objects.nonNull(action)) {
            return String.format("%s (%s)", this.name(), this.action.name().toLowerCase());
        }
        return this.name();
    }
}
