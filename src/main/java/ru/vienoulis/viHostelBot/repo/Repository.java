package ru.vienoulis.viHostelBot.repo;

import java.util.List;
import ru.vienoulis.viHostelBot.dto.Visitor;

public interface Repository {

    void saveVisitor(Visitor visitor);

    List<Visitor> getVisitors();
}
