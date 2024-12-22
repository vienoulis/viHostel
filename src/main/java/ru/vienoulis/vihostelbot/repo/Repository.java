package ru.vienoulis.vihostelbot.repo;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public interface Repository<T> {

    T getTransitionalEntry();

    void setTransitionalEntry(T entry);

    void saveEntry(T entry);

    Optional<T> getEntryBy(Predicate<T> predicate);

    Set<T> getEntrysBy(Predicate<T> predicate);
}
