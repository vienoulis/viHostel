package ru.vienoulis.vihostelbot.process;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.state.StateService;
import ru.vienoulis.vihostelbot.step.Step;
import ru.vienoulis.vihostelbot.step.test.FinalStep;
import ru.vienoulis.vihostelbot.step.test.MiddleStep;
import ru.vienoulis.vihostelbot.step.test.StartStep;

@Component
@Slf4j
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddVisitorProcess extends AbstractProcess {

    private final Queue<Step> steps;
    private final StateService stateService;
    private final StartStep startStep;
    private final MiddleStep middleStep;
    private final FinalStep finalStep;

    @Autowired
    public AddVisitorProcess(StartStep startStep, MiddleStep middleStep, FinalStep finalStep,
            StateService stateService) {
        steps = new LinkedList<>();
        this.startStep = startStep;
        this.middleStep = middleStep;
        this.finalStep = finalStep;
        steps.add(startStep);
        steps.add(middleStep);
        steps.add(finalStep);
        this.stateService = stateService;
    }


    @Override
    public Optional<SendMessage> onMessage(Message message) {
        log.info("onMessage.enter;");

        if (!stateService.isProcessStarted()) {
            log.info("onMessage; start new process");
            stateService.process(this);
        }
        var result = Optional.ofNullable(steps.poll())
                .filter(s -> s.canApplied(message))
                .map(s -> SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(s.processMessage(message))
                        .build());

        if (steps.isEmpty()) {
            log.info("onMessage; finish process");
            stateService.ready();
            reload();
        }

        log.info("onMessage.exit;");
        return result;
    }

    private void reload() {
        log.info("reload.enter;");
        steps.add(startStep);
        steps.add(middleStep);
        steps.add(finalStep);
        log.info("reload.exit;");
    }

    @Override
    public Action getAction() {
        return Action.ADD;
    }
}
