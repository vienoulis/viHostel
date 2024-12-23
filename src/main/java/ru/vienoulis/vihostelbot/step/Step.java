package ru.vienoulis.vihostelbot.step;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Step {

    String getMessage();

    boolean tryApply(Message response);

    String onFail(Message response);

    boolean needResponse();
}
