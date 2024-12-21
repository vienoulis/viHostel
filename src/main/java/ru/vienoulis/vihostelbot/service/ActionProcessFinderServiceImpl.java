package ru.vienoulis.vihostelbot.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.process.AddVisitorProcess;
import ru.vienoulis.vihostelbot.process.Process;

@Slf4j
@Component
public class ActionProcessFinderServiceImpl implements ProcessFinderService {

    private static final String PROCESS_REGEXP_MASK = "^/%s.*%s$";

    private final Map<Action, Process> actionProcessMap;
    @Value("${vienoulis.telegramm.botUsername}")
    private String botUsername;

    @Autowired
    public ActionProcessFinderServiceImpl(AddVisitorProcess addVisitorProcess) {
        actionProcessMap = Map.of(Action.ADD, addVisitorProcess);
    }

    @Override
    public Optional<Process> getProcessCanStart(Message message) {
        var text = message.getText();
        log.info("getProcessCanStart; text: {}", text);
        return Arrays.stream(Action.values())
                .filter(a -> text.matches(PROCESS_REGEXP_MASK.formatted(a.name().toLowerCase(), botUsername)))
                .findFirst()
                .filter(actionProcessMap::containsKey)
                .map(actionProcessMap::get);
    }
}
