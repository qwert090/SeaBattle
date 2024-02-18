package org.packov;

import java.util.*;

public class Ship {

    int numberOfDeck;
    int verticalCoordinate1;
    int verticalCoordinate2;
    int horizontalCoordinate1;
    int horizontalCoordinate2;
    int hp;

    public Ship(Field field, int numberOfDeck, int auto) {
        if (auto == 1) {
            shipBuilder(field.field, numberOfDeck);
        }else if (auto == 2){
            autoShipBuilder(field.field,numberOfDeck);
        }

    }

    public ArrayList<Integer> getVerticalCoordinates(String coordinates) {
        //циферные координаты
        ArrayList<Integer> horizontalCoordinates = new ArrayList<>();
        String horizontalCoordinate0 = coordinates.replaceAll("[A-P]", "");
        String[] horizontalCoordinate1 = horizontalCoordinate0.split("-");
        int horizontalCoordinates2 = Integer.parseInt(horizontalCoordinate1[0]);
        int horizontalCoordinates3 = Integer.parseInt(horizontalCoordinate1[1]);
        horizontalCoordinates.add(horizontalCoordinates2);
        horizontalCoordinates.add(horizontalCoordinates3);
        return horizontalCoordinates;
    }


    public ArrayList<Integer> getHorizontalCoordinates(String coordinates) {
        //буквенные координаты
        ArrayList<Integer> verticalCoordinates = new ArrayList<>();
        String verticalCoordinates0 = coordinates.replaceAll("[1-9]", "");
        String[] verticalCoordinate1 = verticalCoordinates0.split("-");
        String verticalCoordinate2 = verticalCoordinate1[0];
        String verticalCoordinate3 = verticalCoordinate1[1];
        int verticalCoordinateInt1 = verticalCoordinate2.charAt(0) - 64;
        int verticalCoordinateInt2 = verticalCoordinate3.charAt(0) - 64;
        verticalCoordinates.add(verticalCoordinateInt1);
        verticalCoordinates.add(verticalCoordinateInt2);
        return verticalCoordinates;
    }

    public String oneDeckShip(String coordinates) {
        if (coordinates.matches("([A-P])([1-9])([0-6]?)")) {
            coordinates = coordinates + "-" + coordinates;
            return coordinates;
        }
        return coordinates;
    }

    public boolean checkRightNumberOfDeck(String number) {
        return number.matches("([1-6])");

    }


    public boolean checkRightWriting(String coordinates) {
        return coordinates.matches("([A-P])([1-9])([0-6]?)(-)([A-P])([1-9])([0-6]?)");
    }

    //проверка указано ли нужное количество клеток
    public boolean checkRightCoordinates() {
        return (horizontalCoordinate2 - horizontalCoordinate1 != numberOfDeck - 1 ||
                verticalCoordinate2 - verticalCoordinate1 != 0) &&
                (verticalCoordinate2 - verticalCoordinate1 != numberOfDeck - 1 ||
                        horizontalCoordinate2 - horizontalCoordinate1 != 0);
    }

    public int bordresWithField() {
        //1.Случай верхней границы
        if (horizontalCoordinate1 == 1 || horizontalCoordinate2 == 1) {
            //5.Случай левого верхнего угла
            if (verticalCoordinate1 == 1 || verticalCoordinate2 == 1) {
                return 5;
            }
            //6.Случай правого верхнего угла
            if (verticalCoordinate1 == 16 || verticalCoordinate2 == 16) {
                return 6;
            }
            return 1;
        }
        //2.Случай левой границы
        if (verticalCoordinate1 == 1 || verticalCoordinate2 == 1) {
            //7.Случай нижнего левого угла
            if (horizontalCoordinate2 == 16 || horizontalCoordinate1 == 16) {
                return 7;
            }
            return 2;

        }
        //3.Случай правой границы
        if (verticalCoordinate1 == 16 || verticalCoordinate2 == 16) {
            //8.Случай нижнего правного угла
            if (horizontalCoordinate1 == 16 || horizontalCoordinate2 == 16) {
                return 8;
            }
            return 3;

        }
        //4.Случай нижней границы
        if (horizontalCoordinate1 == 16 || horizontalCoordinate2 == 16) {
            return 4;
        }
        //0.Случай непопадания в границы
        return 0;

    }

    public boolean checkFreeField(int variant, String[][] field) {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                switch (variant) {
                    case (0):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (1):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 1) && j <= (verticalCoordinate2)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (2):
                        if (i >= (horizontalCoordinate1 - 1) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (3):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2 - 1) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (4):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2 - 1)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (5):
                        if (i >= (horizontalCoordinate1 - 1) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 1) && j <= (verticalCoordinate2)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (6):
                        if (i >= (horizontalCoordinate1 - 1) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2 - 1)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (7):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2 - 1) &&
                                j >= (verticalCoordinate1 - 1) && j <= (verticalCoordinate2)) {
                            if (!field[i][j].equals(Cell.EMPTY.value)
                                    && !field[i][j].equals(Cell.DEAD.value)) {
                                return false;
                            }
                        }
                    case (8):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2 - 1) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2 - 1)) {
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

    public void shipPlacement(int numberOfDeck, int variant, String[][] field) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                switch (variant) {
                    case (0):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (1):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 1) && j <= (verticalCoordinate2)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (2):
                        if (i >= (horizontalCoordinate1 - 1) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (3):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2 - 1) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (4):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2 - 1)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (5):
                        if (i >= (horizontalCoordinate1 - 1) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 1) && j <= (verticalCoordinate2)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (6):
                        if (i >= (horizontalCoordinate1 - 1) && i <= (horizontalCoordinate2) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2 - 1)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (7):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2 - 1) &&
                                j >= (verticalCoordinate1 - 1) && j <= (verticalCoordinate2)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                    case (8):
                        if (i >= (horizontalCoordinate1 - 2) && i <= (horizontalCoordinate2 - 1) &&
                                j >= (verticalCoordinate1 - 2) && j <= (verticalCoordinate2 - 1)) {
                            field[i][j] = Cell.DEAD.value;
                        }
                }
                String shipName = "\033[0;32m" + (numberOfDeck + 1) + "\033[0m";
                if (verticalCoordinate1 == verticalCoordinate2) {
                    if (j == (verticalCoordinate1 - 1) && i >= (horizontalCoordinate1 - 1)
                            && i <= (horizontalCoordinate2 - 1)) {
                        field[i][j] = shipName;
                    }

                } else if (horizontalCoordinate1 == horizontalCoordinate2) {
                    if (i == (horizontalCoordinate1 - 1) && j >= (verticalCoordinate1 - 1)
                            && j <= (verticalCoordinate2 - 1)) {
                        field[i][j] = shipName;
                    }
                }

            }

        }
    }


    public void shipBuilder(String[][] field, int numberOfDeck) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            this.numberOfDeck = numberOfDeck;
            hp = numberOfDeck;

            System.out.print("Введите координаты нового " + numberOfDeck + "-ти палубного корабля :");
            String coordinats = scanner.next();
            coordinats = oneDeckShip(coordinats);

            if (!checkRightWriting(coordinats)) {
                System.out.println("Неправильно заданы координаты!");
                continue;
            }


            ArrayList<Integer> horizontalCoordinats = getHorizontalCoordinates(coordinats);
            ArrayList<Integer> verticalCoordinats = getVerticalCoordinates(coordinats);
            verticalCoordinate1 = verticalCoordinats.get(0);
            verticalCoordinate2 = verticalCoordinats.get(1);
            horizontalCoordinate1 = horizontalCoordinats.get(0);
            horizontalCoordinate2 = horizontalCoordinats.get(1);
            int variant = bordresWithField();


            if (checkRightCoordinates()) {
                System.out.println("Величина шестипалубного корабля задана неправильно");
                continue;
            }
            if (!checkFreeField(variant, field)) {
                System.out.println("В окрестности заданного поля уже есть корабль");
                continue;
            }
            shipPlacement(numberOfDeck - 1, variant, field);
            break;


        }


    }

    public void autoShipBuilder(String[][] field, int numberOfDeck) {
        this.numberOfDeck = numberOfDeck;
        this.hp = numberOfDeck;
        Random random = new Random();
        int orientation = 1 + random.nextInt(2);

        if (orientation == 1) {
            this.horizontalCoordinate1 = random.nextInt(15 - (numberOfDeck - 1)) + 1;
            this.horizontalCoordinate2 = horizontalCoordinate1 + (numberOfDeck - 1);
            this.verticalCoordinate1 = random.nextInt(15) + 1;
            this.verticalCoordinate2 = verticalCoordinate1;


        } else {
            this.verticalCoordinate1 = random.nextInt(15 - (numberOfDeck - 1)) + 1;
            this.verticalCoordinate2 = verticalCoordinate1 + (numberOfDeck - 1);
            this.horizontalCoordinate1 = random.nextInt(15) + 1;
            this.horizontalCoordinate2 = horizontalCoordinate1;
        }

        outerLoop:
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {

                int variant = bordresWithField();
                if (checkRightCoordinates()) {
                    verticalCoordinate1 = verticalCoordinate1 % 16 + 1;
                    verticalCoordinate2 = verticalCoordinate2 % 16 + 1;
                    continue;
                }
                if (checkFreeField(variant, field)) {
                    shipPlacement(numberOfDeck - 1, variant, field);
                    break outerLoop;

                } else {
                    verticalCoordinate1 = verticalCoordinate1 % 16 + 1;
                    verticalCoordinate2 = verticalCoordinate2 % 16 + 1;
                }
            }
            horizontalCoordinate1 = horizontalCoordinate1 % 16 + 1;
            horizontalCoordinate2 = horizontalCoordinate2 % 16 + 1;
        }
    }
}


