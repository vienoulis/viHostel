package ru.vienoulis.vihostelbot.step.list;

import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vienoulis.vihostelbot.dto.Visitor;
import ru.vienoulis.vihostelbot.repo.Repository;
import ru.vienoulis.vihostelbot.step.Step;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartAllStep implements Step {

    private final Repository<Visitor> visitorRepository;

    @Override
    public String processMessage(Message message) {
        StringJoiner joiner = new StringJoiner(", \n ");
        visitorRepository.getEntrysBy(v -> true).forEach(v -> joiner.add(v.toString()));
        return "Посетители: \n %s".formatted(joiner.toString());
    }

    @Override
    public boolean canApplied(Message message) {
        return true;
    }
}
