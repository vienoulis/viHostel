package ru.vienoulis.vihostelbot.process;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
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
    private final StartStep startStep;
    private final MiddleStep middleStep;
    private final FinalStep finalStep;

    @Autowired
    public AddVisitorProcess(StartStep startStep, MiddleStep middleStep, FinalStep finalStep,
            StateService stateService) {
        super(stateService);
        steps = new LinkedList<>();
        this.startStep = startStep;
        this.middleStep = middleStep;
        this.finalStep = finalStep;
        steps.add(startStep);
        steps.add(middleStep);
        steps.add(finalStep);
    }

    @Override
    protected void onProcessStart() {
        log.info("onProcessStart.enter;");
        stateService.process(this);
        log.info("onProcessStart.exit;");
    }


    @Override
    public Optional<SendMessageBuilder> onMessage(Message message) {
        log.info("onMessage;");
        if (!steps.isEmpty() && !steps.peek().canApplied(message)) {
            return Optional.of(SendMessage.builder().text("Сообщение не принято"));
        }
        return Optional.ofNullable(steps.poll())
                .map(s -> s.processMessage(message))
                .map(s -> SendMessage.builder().text(s));
    }

    @Override
    protected boolean isProcessFinish() {
        return steps.isEmpty();
    }

    @Override
    protected void onProcessFinish() {
        log.info("onFinish.enter;");
        stateService.ready();
        reload();
        log.info("onFinish.exit;");
    }

    @Override
    public Optional<SendMessageBuilder> messageOnCancel(Message message) {
        reload();
        stateService.ready();
        return Optional.of(SendMessage.builder()
                .text("Процесс заселения прерван.")
                .chatId(message.getChatId()));
    }

    private void reload() {
        log.info("reload.enter;");
        steps.clear();
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
