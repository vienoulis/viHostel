package ru.vienoulis.viHostelBot.repo;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vienoulis.viHostelBot.dto.PlaceInRoom;
import ru.vienoulis.viHostelBot.dto.Visitor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class InMemoryRepositoryImplTest {

    @InjectMocks
    private InMemoryRepositoryImpl repository;

    @Test
    void saveVisitor() {
        assertNull(repository.getVisitorBy(__ -> true));
        repository.saveVisitor(Visitor.builder().build());
        assertNotNull(repository.getVisitorBy(__ -> true));
    }

    @Test
    void getVisitorBy() {
        var testName = "test";
        var placeInRoom = PlaceInRoom.builder().room(1).place(1).build();
        repository.saveVisitor(Visitor.builder().name(testName).build());
        repository.saveVisitor(Visitor.builder().placeInRoom(placeInRoom).build());

        assertNotNull(repository.getVisitorBy(v -> Objects.equals(v.getName(), testName)));
        assertNotNull(repository.getVisitorBy(v -> Objects.equals(v.getPlaceInRoom().getRoom(), placeInRoom.getRoom())));
        assertNotNull(repository.getVisitorBy(v -> Objects.equals(v.getPlaceInRoom().getPlace(), placeInRoom.getPlace())));
        assertNotNull(repository.getVisitorBy(v -> Objects.equals(v.getName(), testName)));
        assertNull(repository.getVisitorBy(v -> Objects.equals(v.getName(), testName.substring(2))));
    }
}