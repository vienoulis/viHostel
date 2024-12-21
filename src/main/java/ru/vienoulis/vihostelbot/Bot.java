package ru.vienoulis.vihostelbot;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.vihostelbot.process.AddVisitorProcess;
import ru.vienoulis.vihostelbot.service.ProcessFinderService;
import ru.vienoulis.vihostelbot.state.StateService;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;
    @Getter
    @Value("${vienoulis.telegramm.botUsername}")
    private String botUsername;
    private final TelegramBotsApi telegramBotsApi;
    private final StateService stateService;
    private final ProcessFinderService processFinderService;

    @Autowired
    public Bot(StateService stateService, AddVisitorProcess addVisitorProcess,
            ProcessFinderService processFinderService) {
        this.stateService = stateService;
        this.processFinderService = processFinderService;
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
        var msg = update.getMessage();
        stateService.getCurrentProcessIfExist()
                .or(() -> processFinderService.getProcessCanStart(msg))
                .flatMap(p -> p.onMessage(msg))
                .ifPresent(this::sendMessage);
        log.info("onUpdateReceived.exit; state: {}", stateService.getCurrentState());
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            log.warn("", e);
            throw new RuntimeException(e);
        }
    }
}
