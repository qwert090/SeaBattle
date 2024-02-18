package org.packov;

public enum Cell {
    DEAD("\033[0;31m" + "x" + "\033[0m"),
    EMPTY("\033[0;34m" + "0" + "\033[0m"),
    SHOT("*");

    public final String value;
    Cell(String value) {
        this.value = value;
    }
}
