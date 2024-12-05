package ru.vienoulis.viHostelBot.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import ru.vienoulis.viHostelBot.dto.Visitor;

public class InMemoryRepositoryImpl implements Repository {

    private final List<Visitor> visitors;

    public InMemoryRepositoryImpl() {
        this.visitors = new ArrayList<>();
    }

    @Override
    public void saveVisitor(Visitor visitor) {
        visitors.add(visitor);
    }

    @Override
    public Visitor getVisitorBy(Predicate<Visitor> predicate) {
        return visitors.stream().filter(predicate).findFirst().orElse(null);
    }
}
