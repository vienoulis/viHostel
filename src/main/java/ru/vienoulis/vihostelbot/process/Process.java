package ru.vienoulis.vihostelbot.process;


import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;

public interface Process {

    Action getAction();

    Optional<SendMessage> onMessage(Message message);

    boolean canStartProcess(Message message);

    Optional<SendMessage> messageOnCancel(Message message);
}
