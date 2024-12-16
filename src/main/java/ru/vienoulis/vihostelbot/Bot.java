package ru.vienoulis.vihostelbot;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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
import ru.vienoulis.vihostelbot.process.Process;
import ru.vienoulis.vihostelbot.state.State;
import ru.vienoulis.vihostelbot.state.StateService;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;
    private final TelegramBotsApi telegramBotsApi;
    private final StateService stateService;
    private final AddVisitorProcess addVisitorProcess;
    private final List<Process> subscribers = new ArrayList<>();

    @Autowired
    public Bot(StateService stateService, AddVisitorProcess addVisitorProcess) {
        this.stateService = stateService;
        this.addVisitorProcess = addVisitorProcess;
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
        if (stateService.getCurrentState() == State.READY) {
            addVisitorProcess.start();
            subscribers.add(addVisitorProcess);
        } else if (stateService.isProcessStarted()) {
            subscribers.forEach(s -> s.onMessage(update.getMessage())
                    .map(m -> {
                        m.setChatId(update.getMessage().getChatId());
                        return m;
                    })
                    .ifPresent(this::executeWithoutException));
        }
        log.info("onUpdateReceived.exit; state: {}", stateService.getCurrentState());
    }

    private void executeWithoutException(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            log.warn("", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return "Test viHostelBot";
    }
}
