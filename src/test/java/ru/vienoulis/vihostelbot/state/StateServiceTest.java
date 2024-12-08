package ru.vienoulis.vihostelbot.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class StateServiceTest {

    @InjectMocks
    private StateService stateService;

    @Test
    void setCurrentState() {
        assertEquals(State.INIT, stateService.getCurrentState());
        assertEquals(State.INIT, stateService.getPreviousState());
        assertFalse(stateService.isProcessStarted());

        stateService.ready();
        assertEquals(State.READY, stateService.getCurrentState());
        assertEquals(State.INIT, stateService.getPreviousState());
        assertFalse(stateService.isProcessStarted());

        stateService.error();
        assertEquals(State.ERROR, stateService.getCurrentState());
        assertEquals(State.READY, stateService.getPreviousState());
        assertFalse(stateService.isProcessStarted());
    }
}