package ru.vienoulis.vihostelbot.process;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.service.ConfigProvider;
import ru.vienoulis.vihostelbot.state.StateService;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public abstract class AbstractProcess implements Process {

    private final ConfigProvider configProvider;
    protected final StateService stateService;

    @Override
    public boolean canStartProcess(Message message) {
        var text = message.getText();
        var expected = "/%s%s".formatted(getAction().name().toLowerCase(), configProvider.getBotUsername());
        return StringUtils.equals(expected, text);
    }

    @Override
    public Optional<SendMessage> processAndGetMessage(Message message) {
        log.info("processAndGetMessage; msg text: {} ", message.getText());

        if (!stateService.isProcessStarted()) {
            onProcessStart();
        }

        var resultMsg = onMessage(message)
                .map(smb -> smb.chatId(message.getChatId()));

        if (isProcessFinish()) {
            onProcessFinish();
        }
        return resultMsg
                .map(SendMessageBuilder::build);
    }

    protected abstract void onProcessStart();

    protected abstract boolean isProcessFinish();

    protected abstract Optional<SendMessageBuilder> onMessage(Message message);

    protected abstract void onProcessFinish();

}
