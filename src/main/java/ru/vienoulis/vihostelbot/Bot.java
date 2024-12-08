package ru.vienoulis.vihostelbot;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.vihostelbot.state.StateService;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;
    private final TelegramBotsApi telegramBotsApi;
    private final StateService stateService;

    @Autowired
    public Bot(StateService stateService) {
        this.stateService = stateService;
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
        stateService.ready();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("onUpdateReceived.enter; state: {}", stateService.getCurrentState());
        log.info("onUpdateReceived.exit;");
    }

    @Override
    public String getBotUsername() {
        return "Test viHostelBot";
    }
}
