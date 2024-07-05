package ru.vienoulis.viHostelBot.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@ToString
public class Visitor {

    String name;
    String phone;
    String room;
    boolean isPaid;

}
