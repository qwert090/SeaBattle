package org.packov.utils;

import org.packov.enums.Cell;
import org.packov.entity.Point;
import org.packov.enums.FieldBoundary;

public class ShipValidator {
    private final static String RIGHT_WRITING_PATTERN = "([A-P])([1-9])([0-6]?)(-)([A-P])([1-9])([0-6]?)";

    public static boolean checkRightWriting(String coordinates) {
        return coordinates.matches(RIGHT_WRITING_PATTERN);
    }

    public static boolean checkRightCoordinates(Point coordinateOne, Point coordinateTwo, int numberOfDeck) {
        return (coordinateTwo.getY() - coordinateOne.getY() != numberOfDeck - 1 ||
                coordinateTwo.getX() - coordinateOne.getX() != 0) &&
                (coordinateTwo.getX() - coordinateOne.getX() != numberOfDeck - 1 ||
                        coordinateTwo.getY() - coordinateOne.getY() != 0);
    }
    
    public static boolean isWithoutConflicts(FieldBoundary fieldBoundary, String[][] field, Point coordinateOne, Point coordinateTwo) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                switch (fieldBoundary) {
                    case NON:
                        if (i >= (coordinateOne.getY() - 2) && i <= (coordinateTwo.getY()) &&
                                j >= (coordinateOne.getX() - 2) && j <= (coordinateTwo.getX())) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case TOP:
                        if (i >= (coordinateOne.getY() - 2) && i <= (coordinateTwo.getY()) &&
                                j >= (coordinateOne.getX() - 1) && j <= (coordinateTwo.getX())) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case LEFT:
                        if (i >= (coordinateOne.getY() - 1) && i <= (coordinateTwo.getY()) &&
                                j >= (coordinateOne.getX() - 2) && j <= (coordinateTwo.getX())) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case RIGHT:
                        if (i >= (coordinateOne.getY() - 2) && i <= (coordinateTwo.getY() - 1) &&
                                j >= (coordinateOne.getX() - 2) && j <= (coordinateTwo.getX())) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case BOTTOM:
                        if (i >= (coordinateOne.getY() - 2) && i <= (coordinateTwo.getY()) &&
                                j >= (coordinateOne.getX() - 2) && j <= (coordinateTwo.getX() - 1)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case TOP_LEFT:
                        if (i >= (coordinateOne.getY() - 1) && i <= (coordinateTwo.getY()) &&
                                j >= (coordinateOne.getX() - 1) && j <= (coordinateTwo.getX())) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case TOP_RIGHT:
                        if (i >= (coordinateOne.getY() - 1) && i <= (coordinateTwo.getY()) &&
                                j >= (coordinateOne.getX() - 2) && j <= (coordinateTwo.getX() - 1)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case BOTTOM_LEFT:
                        if (i >= (coordinateOne.getY() - 2) && i <= (coordinateTwo.getY() - 1) &&
                                j >= (coordinateOne.getX() - 1) && j <= (coordinateTwo.getX())) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case BOTTOM_RIGHT:
                        if (i >= (coordinateOne.getY() - 2) && i <= (coordinateTwo.getY() - 1) &&
                                j >= (coordinateOne.getX() - 2) && j <= (coordinateTwo.getX() - 1)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                }
            }
        }
        return true;
    }
}
