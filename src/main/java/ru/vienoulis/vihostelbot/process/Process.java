package ru.vienoulis.vihostelbot.process;


import java.util.Optional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;

public interface Process {

    Action getAction();

    Optional<SendMessage> processAndGetMessage(Message message);

    boolean canStartProcess(Message message);

    Optional<SendMessageBuilder> messageOnCancel(Message message);
}
