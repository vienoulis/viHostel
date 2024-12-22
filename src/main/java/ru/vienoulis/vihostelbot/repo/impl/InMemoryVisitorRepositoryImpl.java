package ru.vienoulis.vihostelbot.repo.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ru.vienoulis.vihostelbot.dto.Visitor;
import ru.vienoulis.vihostelbot.repo.Repository;

public class InMemoryVisitorRepositoryImpl implements Repository<Visitor> {

    private final List<Visitor> visitors;
    private Visitor transitionalEntry = Visitor.builder().build();

    public InMemoryVisitorRepositoryImpl() {
        this.visitors = new ArrayList<>();
        this.visitors.add(Visitor.builder()
                .name("Тестовый Тестер Тестович")
                .placeInRoom(123)
                .phone("+79999999999")
                .paidBefore(LocalDate.now())
                .build());
    }

    @Override
    public Visitor getTransitionalEntry() {
        return transitionalEntry;
    }

    @Override
    public void setTransitionalEntry(Visitor entry) {
        this.transitionalEntry = entry;
    }

    @Override
    public void saveEntry(Visitor visitor) {
        visitors.add(visitor);
    }

    @Override
    public Optional<Visitor> getEntryBy(Predicate<Visitor> predicate) {
        return visitors.stream().filter(predicate).findFirst();
    }

    @Override
    public Set<Visitor> getEntrysBy(Predicate<Visitor> predicate) {
        return visitors.stream().filter(predicate).collect(Collectors.toSet());
    }
}
