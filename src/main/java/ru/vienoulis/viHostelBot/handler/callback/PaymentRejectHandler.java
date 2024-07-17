package ru.vienoulis.viHostelBot.handler.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.vienoulis.viHostelBot.model.CallBack;
import ru.vienoulis.viHostelBot.service.CheckInService;
import ru.vienoulis.viHostelBot.state.State;
import ru.vienoulis.viHostelBot.state.State.CheckInSubState;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentRejectHandler extends AbstractCallbackHandler {

    private final CheckInService checkInService;

    @Override
    public CallBack callBack() {
        return CallBack.PAYMENT_REJECT;
    }

    @Override
    public void enrich(CallbackQuery callbackQuery, SendMessageBuilder msgToSend) {
        log.info("enrich.enter;");
        checkInService.setIsPaid(false);
        // TODO Если оплата не завершена, возможно стоит временно сохранить с пометкой не оплачен
        checkInService.clear();
        stateMachine.transitTo(State.STARTED);
        log.info("enrich.exit;");
    }

    @Override
    public boolean validate(CallbackQuery callbackQuery) {
        var currentState = stateMachine.currentState();
        return currentState == State.CHECK_IN && currentState.getSubState() == CheckInSubState.NEED_PAY;
    }
}
