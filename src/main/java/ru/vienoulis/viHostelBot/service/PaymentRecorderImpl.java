package ru.vienoulis.viHostelBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRecorderImpl implements PaymentRecorder {

    @Override
    public void processData(String data) {
        log.info("processData.enter; data: {}", data);

    }
}
