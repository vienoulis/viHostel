package ru.vienoulis.vihostelbot.process;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Action;
import ru.vienoulis.vihostelbot.service.ConfigProvider;
import ru.vienoulis.vihostelbot.state.StateService;
import ru.vienoulis.vihostelbot.step.Step;
import ru.vienoulis.vihostelbot.step.StepGenerator;

@Slf4j
public class MultyStepProcess extends AbstractProcess {

    private final Queue<Step> steps = new LinkedList<>();
    private final StepGenerator stepGenerator;
    private final AtomicReference<Step> awaitResponseStep = new AtomicReference<>();

    public MultyStepProcess(StateService stateService, StepGenerator stepGenerator, ConfigProvider configProvider) {
        super(configProvider, stateService);
        this.stepGenerator = stepGenerator;
    }

    @Override
    protected void onProcessStart() {
        log.info("onProcessStart.enter;");
        stateService.process(this);
        steps.addAll(stepGenerator.getStep());
        log.info("onProcessStart.exit;");
    }

    @Override
    public Optional<SendMessageBuilder> onMessage(Message message) {
        log.info("onMessage; text: {}", message.getText());
        return tryApplyIfNeedResponse(message)
                .or(this::startNextStepMessage)
                .map(s -> SendMessage.builder().text(s));
    }


    private Optional<String> tryApplyIfNeedResponse(Message message) {
        log.info("tryApplyIfNeedResponse;enter;");
        var needResponseStep = awaitResponseStep.get();

        if (Objects.isNull(needResponseStep)) {
            log.info("tryApplyIfNeedResponse.exit; hasn't step for response");
            return Optional.empty();
        }

        if (needResponseStep.tryApply(message)) {
            log.info("tryApplyIfNeedResponse; message apply success, drop await response step and try get next step.");
            awaitResponseStep.set(null);
            return Optional.empty();
        }

        log.info("tryApplyIfNeedResponse.exit; apply fail, return fail message");
        return Optional.of(needResponseStep.onFail(message));
    }

    @Override
    protected boolean isProcessFinish() {
        log.debug("isProcessFinish.enter;");
        var result = Objects.isNull(awaitResponseStep.get()) && steps.isEmpty();
        log.debug("isProcessFinish.exit;");
        return result;
    }

    @Override
    protected void onProcessFinish() {
        log.info("onFinish.enter;");
        stateService.ready();
        reload();
        log.info("onFinish.exit;");
    }

    @Override
    public Action getAction() {
        return stepGenerator.getAction();
    }

    @Override
    public Optional<SendMessageBuilder> messageOnCancel(Message message) {
        log.debug("messageOnCancel.enter;");
        reload();
        stateService.ready();
        var result = Optional.of(SendMessage.builder()
                .text(stepGenerator.onCancelMessage())
                .chatId(message.getChatId()));
        log.debug("messageOnCancel.exit; result: {}", result);
        return result;
    }

    private void reload() {
        log.info("reload.enter;");
        steps.clear();
        awaitResponseStep.set(null);
        log.info("reload.exit;");
    }


    private Optional<String> startNextStepMessage() {
        return Optional.ofNullable(steps.poll())
                .map(this::saveStepIfNeedResponse)
                .map(Step::getMessage);
    }

    private Step saveStepIfNeedResponse(Step step) {
        if (step.needResponse()) {
            awaitResponseStep.set(step);
        }
        return step;
    }
}
