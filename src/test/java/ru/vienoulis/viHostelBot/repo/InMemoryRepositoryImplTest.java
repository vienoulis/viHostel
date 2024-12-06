package ru.vienoulis.viHostelBot.repo;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vienoulis.viHostelBot.dto.PlaceInRoom;
import ru.vienoulis.viHostelBot.dto.Visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InMemoryRepositoryImplTest {

    private static final String TEST_NAME = "testName";
    private static final PlaceInRoom PLACE_IN_ROOM = PlaceInRoom.builder().room(1).place(1).build();

    @InjectMocks
    private InMemoryRepositoryImpl repository;

    @Test
    void saveVisitor() {
        assertTrue(repository.getVisitorBy(__ -> true).isEmpty());
        repository.saveVisitor(Visitor.builder().build());
        assertTrue(repository.getVisitorBy(__ -> true).isPresent());
    }

    @Test
    void getVisitorBy() {
        repository.saveVisitor(Visitor.builder().name(TEST_NAME).build());
        repository.saveVisitor(Visitor.builder().placeInRoom(PLACE_IN_ROOM).build());

        assertTrue(repository.getVisitorBy(v -> Objects.equals(v.getName(), TEST_NAME)).isPresent());
        assertTrue(repository.getVisitorBy(v -> v.getPlaceInRoom() != null &&
                Objects.equals(v.getPlaceInRoom().getRoom(), PLACE_IN_ROOM.getRoom())).isPresent());
        assertTrue(repository.getVisitorBy(v -> v.getPlaceInRoom() != null &&
                Objects.equals(v.getPlaceInRoom().getPlace(), PLACE_IN_ROOM.getPlace())).isPresent());
        assertTrue(repository.getVisitorBy(v ->
                Objects.equals(v.getName(), TEST_NAME)).isPresent());
        assertTrue(repository.getVisitorBy(v ->
                Objects.equals(v.getName(), TEST_NAME.substring(2))).isEmpty());
    }

    @Test
    void getVisitorsBy() {

        repository.saveVisitor(Visitor.builder().name(TEST_NAME + 1).build());
        repository.saveVisitor(Visitor.builder().name(TEST_NAME + 2).build());
        repository.saveVisitor(Visitor.builder().name(TEST_NAME + 3).build());
        repository.saveVisitor(Visitor.builder().name("").build());
        repository.saveVisitor(Visitor.builder().placeInRoom(PLACE_IN_ROOM).build());

        assertEquals(5, repository.getVisitorsBy(v -> true).size());
        assertEquals(4, repository.getVisitorsBy(v -> Objects.nonNull(v.getName())).size());
        assertEquals(3, repository.getVisitorsBy(v -> StringUtils.startsWith(v.getName(), TEST_NAME)).size());
        assertEquals(1, repository.getVisitorsBy(v -> StringUtils.equals(v.getName(), TEST_NAME + 1)).size());
    }
}