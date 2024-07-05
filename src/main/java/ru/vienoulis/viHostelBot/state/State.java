package ru.vienoulis.viHostelBot.state;

import java.util.concurrent.atomic.AtomicReference;

public enum State {

    IDLE,
    STARTED,
    CHECK_IN;

    private final AtomicReference<CheckInSubState> checkInSubState = new AtomicReference<>();

    public void setSubState(CheckInSubState subState) {
        checkInSubState.set(subState);
    }

    public CheckInSubState getSubState() {
        return checkInSubState.get();
    }

    public enum CheckInSubState {
        NEED_NAME,
        NEED_PHONE,
        NEED_ROOM
    }
}
