package ru.vienoulis.vihostelbot.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@ToString
public class Visitor {

    String name;
    String phone;
    //    PlaceInRoom placeInRoom;
    int placeInRoom;
    LocalDate paidBefore;
}
