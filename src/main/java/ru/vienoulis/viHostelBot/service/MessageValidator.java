package ru.vienoulis.viHostelBot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageValidator {

    boolean isPaymentRecordData(String text);

    boolean hasText(Message message);

}
