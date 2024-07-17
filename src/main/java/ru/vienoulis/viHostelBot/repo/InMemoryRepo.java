package ru.vienoulis.viHostelBot.repo;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vienoulis.viHostelBot.dto.Visitor;

@Slf4j
@Service
public class InMemoryRepo implements Repository {

    private final List<Visitor> visitors = new ArrayList<>();

    @Override
    public void saveVisitor(Visitor visitor) {
        log.info("saveVisitor.enter; visitor: {}", visitor);
        visitors.add(visitor);
        log.info("saveVisitor.exit;");
    }

    @Override
    public List<Visitor> getVisitors() {
        log.info("getVisitors;");
        return visitors;
    }
}
