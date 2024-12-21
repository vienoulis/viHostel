package ru.vienoulis.vihostelbot.service;

import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.process.AddVisitorProcess;
import ru.vienoulis.vihostelbot.process.Process;

@Slf4j
@Component
public class ActionProcessFinderServiceImpl implements ProcessFinderService {

    private final Set<Process> processSet;

    @Autowired
    public ActionProcessFinderServiceImpl(AddVisitorProcess addVisitorProcess) {
        processSet = Set.of(addVisitorProcess);
    }

    @Override
    public Optional<Process> getProcessCanStart(Message message) {
        var text = message.getText();
        log.info("getProcessCanStart; text: {}", text);
        return processSet.stream()
                .filter(p -> p.canStartProcess(message))
                .findFirst();
    }
}
