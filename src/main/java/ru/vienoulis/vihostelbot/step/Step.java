package ru.vienoulis.vihostelbot.step;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Step {

    SendMessage processMessage(Message message);

}
