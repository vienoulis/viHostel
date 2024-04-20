package ru.vienoulis.viHostelBot.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class MessageValidatorImpl implements MessageValidator {

    private static final String REGEX = "\\w+\\s\\w+\\s\\d+\\s\\d+\\s\\d+";

    @Override
    public boolean isPaymentRecordData(String text) {
        //todo проверить как это в таблице
//        String regex = "\\w+\\s\\w+\\s\\d{3}\\s\\d{3}\\s\\d{3}";
//        return text.matches(REGEX);
        return StringUtils.isNotBlank(text);
    }

    @Override
    public boolean hasText(Message message) {
        return StringUtils.isNotBlank(message.getText());
    }
}
