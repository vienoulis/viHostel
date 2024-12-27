package ru.vienoulis.vihostelbot.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SheetsVisitor {

    String room;
    String fio;
    LocalDate startDate;
    LocalDate endDate;
    String contact;

    @Override
    public String toString() {
        return "%s, %s, %s".formatted(fio, endDate, contact);
    }
}
