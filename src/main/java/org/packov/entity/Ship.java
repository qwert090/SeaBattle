package org.packov.entity;

import lombok.Getter;
import lombok.Setter;
import org.packov.enums.FieldBoundary;

@Getter
@Setter
public class Ship {
    private int numberOfDeck;
    private Point coordinateOne;
    private Point coordinateTwo;
    private int hp;
    private FieldBoundary fieldBoundary;
}


