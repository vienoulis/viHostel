package ru.vienoulis.viHostelBot.repo;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import ru.vienoulis.viHostelBot.dto.Visitor;

public interface Repository {

    void saveVisitor(Visitor visitor);

    Optional<Visitor> getVisitorBy(Predicate<Visitor> predicate);

    Set<Visitor> getVisitorsBy(Predicate<Visitor> predicate);
}
