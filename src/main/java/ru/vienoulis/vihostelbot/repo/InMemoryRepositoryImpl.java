package ru.vienoulis.vihostelbot.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ru.vienoulis.vihostelbot.dto.Visitor;

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
    public Optional<Visitor> getVisitorBy(Predicate<Visitor> predicate) {
        return visitors.stream().filter(predicate).findFirst();
    }

    @Override
    public Set<Visitor> getVisitorsBy(Predicate<Visitor> predicate) {
        return visitors.stream().filter(predicate).collect(Collectors.toSet());
    }
}
