package ru.vienoulis.viHostelBot;

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
import ru.vienoulis.viHostelBot.service.MessageValidator;
import ru.vienoulis.viHostelBot.service.PaymentRecorder;

@Getter
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${vienoulis.telegramm.token}")
    private String botToken;
    private final TelegramBotsApi telegramBotsApi;
    private final MessageValidator messageValidator;
    private final PaymentRecorder paementRecorder;


    @Autowired
    public Bot(MessageValidator messageValidator, PaymentRecorder paementRecorder) {
        this.messageValidator = messageValidator;
        this.paementRecorder = paementRecorder;
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
        log.info("onUpdateReceived;");
        if (!messageValidator.hasText(update.getMessage())) {
            log.info("message has no text");
            return;
        }
        var data = update.getMessage().getText();
        if (messageValidator.isPaymentRecordData(data)) {
            log.info("onUpdateReceived; isPaymentRecord");
            paementRecorder.processData(data);
        }
    }

    @Override
    public String getBotUsername() {
        return "Test viHostelBot";
    }
}
