package ru.vienoulis.vihostelbot.process;


import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Process {

    Optional<SendMessage> onMessage(Message message);

    boolean canStartProcess(Message message);
}
