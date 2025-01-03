package ru.vienoulis.vihostelbot.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@ToString
public class PlaceInRoom {
    int room;
    int place;
}
