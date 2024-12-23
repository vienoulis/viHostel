package ru.vienoulis.vihostelbot.step;

import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractFinishStep implements Step {

    @Override
    public final boolean tryApply(Message response) {
        throw new UnsupportedOperationException(
                "FinalStep doesn't need a tryApply because it doesn't need an response.");
    }

    @Override
    public final String onFail(Message response) {
        throw new UnsupportedOperationException("FinalStep doesn't need a onFail because it doesn't need an response.");
    }

    @Override
    public final boolean needResponse() {
        return false;
    }
}
