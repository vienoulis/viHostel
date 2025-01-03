package ru.vienoulis.vihostelbot;

import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.vihostelbot.process.CancelProcess;
import ru.vienoulis.vihostelbot.process.Process;
import ru.vienoulis.vihostelbot.service.ConfigProvider;
import ru.vienoulis.vihostelbot.service.ProcessFinderService;
import ru.vienoulis.vihostelbot.state.StateService;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    private final ConfigProvider configProvider;
    private final TelegramBotsApi telegramBotsApi;
    private final StateService stateService;
    private final ProcessFinderService processFinderService;
    private final CancelProcess cancelProcess;

    @Autowired
    public Bot(ConfigProvider configProvider, StateService stateService,
            ProcessFinderService processFinderService,
            CancelProcess cancelProcess) {
        this.configProvider = configProvider;
        this.stateService = stateService;
        this.processFinderService = processFinderService;
        this.cancelProcess = cancelProcess;
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
        log.info("onUpdateReceived.enter; id: {}", update.getUpdateId());
        var msg = update.getMessage();
        getCancelProcessIfExist(msg)
                .or(stateService::getCurrentProcessIfExist)
                .or(() -> processFinderService.getProcessCanStart(msg))
                .flatMap(p -> p.processAndGetMessage(msg))
                .ifPresent(this::sendMessage);
        log.info("onUpdateReceived.exit; id: {}", update.getUpdateId());
    }

    @Override
    public String getBotUsername() {
        return configProvider.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return configProvider.getToken();
    }

    private Optional<Process> getCancelProcessIfExist(Message msg) {
        return Optional.<Process>of(cancelProcess)
                .filter(p -> p.canStartProcess(msg));
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            log.info("sendMessage: text: {}", sendMessage.getText());
            execute(sendMessage);
        } catch (Exception e) {
            log.warn("", e);
            throw new RuntimeException(e);
        }
    }
}
