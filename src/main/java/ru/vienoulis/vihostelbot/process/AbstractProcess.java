package ru.vienoulis.vihostelbot.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
public abstract class AbstractProcess implements Process {

    @Value("${vienoulis.telegramm.botUsername}")
    private String botUsername;

    @Override
    public boolean canStartProcess(Message message) {
        var text = message.getText();
        var expected = "/%s%s".formatted(getAction().name().toLowerCase(), botUsername);
        return StringUtils.equals(expected, text);
    }
}
