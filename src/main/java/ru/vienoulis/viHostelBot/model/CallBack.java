package ru.vienoulis.viHostelBot.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public enum CallBack {

    PAYMENT_DONE("Оплатил"),
    PAYMENT_REJECT("Не оплатил");

    private final String btnText;

    CallBack(String btnText) {
        this.btnText = btnText;
    }

    public InlineKeyboardButton createBtn() {
        return InlineKeyboardButton.builder()
                .text(btnText)
                .callbackData(name())
                .build();
    }

}
