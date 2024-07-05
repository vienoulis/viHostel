package ru.vienoulis.viHostelBot.service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vienoulis.viHostelBot.dto.Visitor;

@Slf4j
@Service
public class CheckInService {

    private final AtomicReference<Visitor> visitor = new AtomicReference<>(null);

    public void start() {
        log.info("start.enter;");
        visitor.set(Visitor.builder().build());
        log.info("start.exit;");
    }

    public void setName(String name) {
        log.info("setName.enter; current visitor: {}, name to set: {}", visitor.get(), name);
        if (Objects.isNull(visitor.get())) {
            throw new RuntimeException("setName.error; Check in not started;");
        }
        visitor.updateAndGet(v -> v.toBuilder().name(name).build());
        log.info("setName.exit;");
    }

    public void setPhone(String phone) {
        log.info("setPhone.enter; current visitor: {}, phone to set: {}", visitor.get(), phone);
        if (Objects.isNull(visitor.get())) {
            throw new RuntimeException("setPhone.error; Check in not started;");
        }
        visitor.updateAndGet(v -> v.toBuilder().phone(phone).build());
        log.info("setPhone.exit;");
    }

    public void setRoom(String room) {
        log.info("setRoom.enter; current visitor: {}, room to set: {}", visitor.get(), room);
        if (Objects.isNull(visitor.get())) {
            throw new RuntimeException("setRoom.error; Check in not started;");
        }
        visitor.updateAndGet(v -> v.toBuilder().room(room).build());
        log.info("setRoom.exit;");
    }

    public void setIsPaid(boolean isPaid) {
        log.info("setIsPaid.enter; current visitor: {}, room to set: {}", visitor.get(), isPaid);
        if (Objects.isNull(visitor.get())) {
            throw new RuntimeException("setIsPaid.error; Check in not started;");
        }
        visitor.updateAndGet(v -> v.toBuilder().isPaid(isPaid).build());
        log.info("setIsPaid.exit;");
    }

    private void clear() {
        log.info("clear.enter;");
        visitor.set(null);
        log.info("clear.exit;");
    }

}
