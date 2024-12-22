package ru.vienoulis.vihostelbot.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Visitor {

    String name;
    String phone;
    //    PlaceInRoom placeInRoom;
    int placeInRoom;
    LocalDate paidBefore;

    @Override
    public String toString() {
        return "Имя: '%s', телефон: '%s', комната: '%s', оплата: '%s'".formatted(name, phone, placeInRoom, paidBefore);
    }
}
