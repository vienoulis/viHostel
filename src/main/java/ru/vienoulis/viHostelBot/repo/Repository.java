package ru.vienoulis.viHostelBot.repo;

import java.util.function.Predicate;
import ru.vienoulis.viHostelBot.dto.Visitor;

public interface Repository {

    void saveVisitor(Visitor visitor);

    Visitor getVisitorBy(Predicate<Visitor> predicate);
}
