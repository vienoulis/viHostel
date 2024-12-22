package ru.vienoulis.vihostelbot.service;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.process.Process;

public interface ProcessFinderService {

    Optional<Process> getProcessCanStart(Message message);
}
