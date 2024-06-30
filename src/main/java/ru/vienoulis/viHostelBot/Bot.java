package ru.vienoulis.viHostelBot;

import jakarta.annotation.PostConstruct;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;
    private final TelegramBotsApi telegramBotsApi;


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
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("onUpdateReceived.enter;");
        if (hasText(update)) {
            log.info("onUpdateReceived; has text: {}", update.getMessage().getText());
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
}
