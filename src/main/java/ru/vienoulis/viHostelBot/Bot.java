package ru.vienoulis.viHostelBot;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.viHostelBot.handler.ViHostelHandler;
import ru.vienoulis.viHostelBot.service.HandlersProcessor;
import ru.vienoulis.viHostelBot.state.State;
import ru.vienoulis.viHostelBot.state.StateMachine;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;
    private final TelegramBotsApi telegramBotsApi;
    @Inject
    private HandlersProcessor handlersProcessor;
    @Inject
    private StateMachine stateMachine;

    @Autowired
    public Bot() {
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            log.error("Constructor Bot error: ", e);
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        telegramBotsApi.registerBot(this);
        stateMachine.transitTo(State.STARTED);
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("onUpdateReceived.enter;");
        if (hasText(update)) {
            log.info("onUpdateReceived; has text: {}", update.getMessage().getText());
            var message = SendMessage.builder().chatId(update.getMessage().getChatId());
            handlersProcessor.getAppliebleHandlers(update.getMessage())
                    .forEach(h -> enrichAndSendMessage(h, message));
        }
        log.info("onUpdateReceived.exit;");
    }

    @Override
    public String getBotUsername() {
        return "Test viHostelBot";
    }

    private boolean hasText(Update update) {
        if (Objects.isNull(update) || Objects.isNull(update.getMessage())) {
            log.info("hasText; Update or message is null");
            return false;
        }

        return StringUtils.isNotBlank(update.getMessage().getText());
    }

    private void enrichAndSendMessage(ViHostelHandler h, SendMessageBuilder message) {
        try {
            log.info("enrichAndSendMessage.enter;");
            h.enrich(message);
            execute(message.build());
            log.info("enrichAndSendMessage.exit;");
        } catch (TelegramApiException e) {
            log.error("enrichAndSendMessage.error;", e);
            throw new RuntimeException(e);
        }
    }
}
