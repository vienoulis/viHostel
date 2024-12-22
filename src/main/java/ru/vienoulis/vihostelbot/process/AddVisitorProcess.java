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

    @Autowired
    public AddVisitorProcess(StartStep startStep, MiddleStep middleStep, FinalStep finalStep,
            StateService stateService) {
        steps = new LinkedList<>();
        steps.add(startStep);
        steps.add(middleStep);
        steps.add(finalStep);
        this.stateService = stateService;
    }


    @Override
    public Optional<SendMessage> onMessage(Message message) {
        log.info("onMessage;");
        if (!stateService.isProcessStarted()) {
            stateService.process(this);
        }
        return Optional.ofNullable(steps.poll())
                .map(s -> SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(s.processMessage(message))
                        .build());
    }

    @Override
    public Action getAction() {
        return Action.ADD;
    }
}
