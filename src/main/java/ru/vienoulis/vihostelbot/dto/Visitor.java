package ru.vienoulis.vihostelbot.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@ToString
public class Visitor {
    String name;
    String phone;
    PlaceInRoom placeInRoom;
    Date paidBefore;
}
