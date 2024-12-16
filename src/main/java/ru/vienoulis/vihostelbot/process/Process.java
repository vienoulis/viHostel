package ru.vienoulis.vihostelbot.process;


import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;

public interface Process {

    void start();

    void onMessage(Message message);

    Action getAction();
}
