package ru.vienoulis.vihostelbot.step;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Step {

    String processMessage(Message message);

}
