package org.packov.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.packov.enums.Cell;

@AllArgsConstructor
@Getter
public class Field {
    private String[][] field;

    //Создаем поле
    public static String[][] createField(){
    String[][] field = new String[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                field[i][j] = Cell.EMPTY.value;
            }
        }
        return field;
    }

    public void cleanField(){
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if(field[i][j].equals(Cell.DEAD.value)){
                    field[i][j] = Cell.EMPTY.value;
                }
            }
        }
    }

    public void placeShip(Ship ship) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                switch (ship.getFieldBoundary()) {
                    case NON:
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX())) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case TOP:
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 1) && j <= (ship.getCoordinateTwo().getX())) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case LEFT:
                        if (i >= (ship.getCoordinateOne().getY() - 1) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX())) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case RIGHT:
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY() - 1) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX())) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case BOTTOM:
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX() - 1)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case TOP_LEFT:
                        if (i >= (ship.getCoordinateOne().getY() - 1) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 1) && j <= (ship.getCoordinateTwo().getX())) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case TOP_RIGHT:
                        if (i >= (ship.getCoordinateOne().getY() - 1) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX() - 1)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case BOTTOM_LEFT:
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY() - 1) &&
                                j >= (ship.getCoordinateOne().getX() - 1) && j <= (ship.getCoordinateTwo().getX())) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case BOTTOM_RIGHT:
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY() - 1) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX() - 1)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                }
                String shipName = "\033[0;32m" + (ship.getNumberOfDeck()) + "\033[0m";
                if (ship.getCoordinateOne().getX() == ship.getCoordinateTwo().getX()) {
                    if (j == (ship.getCoordinateOne().getX() - 1) && i >= (ship.getCoordinateOne().getY() - 1)
                            && i <= (ship.getCoordinateTwo().getY() - 1)) {
                        field[i][j] = shipName;
                    }

                } else if (ship.getCoordinateOne().getY() == ship.getCoordinateTwo().getY()) {
                    if (i == (ship.getCoordinateOne().getY() - 1) && j >= (ship.getCoordinateOne().getX() - 1)
                            && j <= (ship.getCoordinateTwo().getX() - 1)) {
                        field[i][j] = shipName;
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 17; i++) {
            stringBuilder.append(String.format("\033[0;33m" + "%3s" + "\033[0m" + "|", i));
        }
        String[] horizontalCoordinates = {"A", "B", "C", "D", "E", "F", "G", "H","I","J","K","L","M","N","O","P" };
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(String.format("\n\033[0;33m" + "%2s" + "\033[0m" + " | ",horizontalCoordinates[i]));
            for (int j = 0; j < 16; j++) {
                stringBuilder.append(field[i][j]).append(" | ");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
