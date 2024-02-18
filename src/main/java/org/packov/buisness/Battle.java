package org.packov.buisness;

import org.packov.enums.Cell;
import org.packov.entity.Field;
import org.packov.entity.Player;
import org.packov.entity.Ship;
import org.packov.utils.FieldUtil;
import org.packov.utils.InputUtil;

import java.util.ArrayList;
import java.util.Random;

public class Battle {


    public static void pressEnter() {
        System.out.println("Нажмите ENTER");
        InputUtil.inString();
        for (int i = 0; i < 30; i++) {
            System.out.println();

        }
    }

    public String attack(Field playerField, Field playerObserveField, Field enemyField, ArrayList<Ship> ships, Player player) {
        while (true) {
            FieldUtil.showTwoFields(playerField, playerObserveField);
            System.out.println(player.name() + " введите поле для атаки :");
            String coordinates = InputUtil.inNotNullString();
            if (!checkRightWriting(coordinates)) {
                System.out.println("Неправильно введены координаты поля атаки");
                continue;
            }
            int verticalCoordinate = getVerticalCoordinate(coordinates);
            int horizontalCoordinate = getHorizontalCoordinate(coordinates);
            if (enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 1].equals(Cell.EMPTY.value)) {
                playerObserveField.getField()[horizontalCoordinate - 1][verticalCoordinate - 1] = Cell.SHOT.value;
                enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 1] = Cell.SHOT.value;
                FieldUtil.showTwoFields(playerField, playerObserveField);
                return "Промах";

            } else {
                if (enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 1].equals(Cell.SHOT.value) ||
                        enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 1].equals(Cell.DEAD.value)) {
                    System.out.println("В это поле вы уже стреляли или здесь точно не расположен корабль");
                } else {
                    String hit = hitShip(verticalCoordinate, horizontalCoordinate, ships, enemyField, playerObserveField);
                    System.out.println(hit);
                    FieldUtil.showTwoFields(playerField, playerObserveField);
                    if (hit.equals("убил") && ships.isEmpty()) {
                        return player.name() + " победил";
                    }
                    System.out.println("Продолжайте атаковать");
                    attack(playerField, playerObserveField, enemyField, ships, player);
                    return "Промах";
                }
            }
        }
    }

    public int[] autoAttack(
            Field playerObserveField,
            Field enemyField,
            ArrayList<Ship> ships,
            int[] nextAttack
    ) {
        System.out.println("Бот атакует");
        Random random = new Random();
        int verticalCoordinateAttack;
        int horizontalCoordinateAttack;
        if (nextAttack[0] == 0) {
            nextAttack[0] = random.nextInt(15) + 1;
            nextAttack[1] = random.nextInt(15) + 1;
        }

        while (true) {
            horizontalCoordinateAttack = nextAttack[0];
            verticalCoordinateAttack = nextAttack[1];
            if(nextAttack[2] == 3){
               nextAttack = shipDetected(nextAttack,enemyField,playerObserveField,ships);
                horizontalCoordinateAttack = nextAttack[0];
                verticalCoordinateAttack = nextAttack[1];
               if(nextAttack[2] == 4){
                   nextAttack[2] = 3;
                   return nextAttack;
               }
            }


            if (nextAttack[2] == 2) {
                nextAttack = whichWay(
                        nextAttack[0],
                        nextAttack[1],
                        playerObserveField,
                        enemyField,
                        ships

                );
                horizontalCoordinateAttack = nextAttack[0];
                verticalCoordinateAttack = nextAttack[1];
                }
            if (nextAttack[2] == 2){
                return nextAttack;
            }

            //первая атака
            else if (nextAttack[2] == 0) {
                System.out.println("бот атакует поле " + (char) (horizontalCoordinateAttack + 64) + verticalCoordinateAttack);
                if (enemyField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1].equals(Cell.EMPTY.value)) {
                    enemyField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1] = Cell.SHOT.value;
                    playerObserveField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1] = Cell.SHOT.value;
                    System.out.println("в первый раз промазал");
                    nextAttack = searchShip(playerObserveField.getField(), ships);
                    return nextAttack;
                } else {
                    System.out.println("бот атакует поле " + (char) (horizontalCoordinateAttack + 64) + verticalCoordinateAttack);
                    String result = hitShip(
                            verticalCoordinateAttack,
                            horizontalCoordinateAttack,
                            ships,
                            enemyField,
                            playerObserveField);
                    if ("убил".equals(result)) {
                        System.out.println("убил с первого раза");
                        nextAttack = searchShip(playerObserveField.getField(), ships);
                        nextAttack[2] = 0;
                    } else {
                        System.out.println("попал с первого раза");
                        nextAttack[0] = horizontalCoordinateAttack;
                        nextAttack[1] = verticalCoordinateAttack;
                        nextAttack[2] = 2;
                    }
                }
            }
        }
    }

    public int[] shipDetected(int[] nextAttack, Field enemyField, Field playerObserveField, ArrayList<Ship> ships) {
        int horizontalCoordinateAttack = nextAttack[0];
        int verticalCoordinateAttack = nextAttack[1];
        int variantOfWay = nextAttack[3];


        while (true) {
            int variant = bordresWithField(horizontalCoordinateAttack,verticalCoordinateAttack);
            if((variant == 1 || variant == 5 || variant == 6) && variantOfWay == 1 ){
                variantOfWay = 2;
                nextAttack[0] = horizontalCoordinateAttack + 1;
            }else if((variant == 6 || variant == 3 || variant == 8) && variantOfWay == 3 ){
                variantOfWay = 4;
                nextAttack[1] = verticalCoordinateAttack - 1;
            }
            if (enemyField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1].equals(Cell.DEAD.value)) {
                switch (variantOfWay) {
                    case (1) -> horizontalCoordinateAttack--;
                    case (2) -> horizontalCoordinateAttack++;
                    case (3) -> verticalCoordinateAttack++;
                    case (4) -> verticalCoordinateAttack--;

                }
                nextAttack[0] = horizontalCoordinateAttack;
                nextAttack[1] = verticalCoordinateAttack;
            } else if (enemyField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1].equals(Cell.SHOT.value))
                switch (variantOfWay) {
                    case (1) -> {
                        nextAttack[3] = 2;
                        nextAttack[0] = horizontalCoordinateAttack + 1;
                    }
                    case (2) -> {
                        nextAttack[3] = 1;
                        nextAttack[0] = horizontalCoordinateAttack - 1;
                    }
                    case (3) -> {
                        nextAttack[3] = 4;
                        nextAttack[1] = verticalCoordinateAttack - 1;
                    }
                    case (4) -> {
                        nextAttack[3] = 3;
                        nextAttack[1] = verticalCoordinateAttack + 1;
                    }
                }
            else if (enemyField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1].equals(Cell.EMPTY.value)) {
                playerObserveField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1] = Cell.SHOT.value;
                enemyField.getField()[horizontalCoordinateAttack - 1][verticalCoordinateAttack - 1] = Cell.SHOT.value;
                System.out.println("промазал при добивании");
                switch (variantOfWay) {
                    case (1) -> {
                        return new int[]{horizontalCoordinateAttack + 1, verticalCoordinateAttack, 4, 2};
                    }
                    case (2) -> {
                        return new int[]{horizontalCoordinateAttack - 1, verticalCoordinateAttack, 4, 1};
                    }
                    case (3) -> {
                        return new int[]{horizontalCoordinateAttack, verticalCoordinateAttack - 1, 4, 4};
                    }
                    case (4) -> {
                        return new int[]{horizontalCoordinateAttack, verticalCoordinateAttack + 1, 4, 3};
                    }

                }
            } else {
                String result = hitShip(
                        verticalCoordinateAttack,
                        horizontalCoordinateAttack,
                        ships,
                        enemyField,
                        playerObserveField
                );
                if ("попал".equals(result)) {
                    System.out.println("попал при добивании");
                } else {
                    System.out.println("убил при добивании");
                    nextAttack = searchShip(playerObserveField.getField(), ships);
                    nextAttack[2] = 0;
                    nextAttack[3] = 0;
                    return nextAttack;
                }

            }
        }
    }

    public int[] whichWay(
            int horizontalCoordinate,
            int verticalCoordinate,
            Field playerObserveField,
            Field enemyField,
            ArrayList<Ship> ships
    ) {
        int variant = bordresWithField(horizontalCoordinate,verticalCoordinate);


        //проверяем рядом стоящие поля после ранения
        //сверху
        if (variant != 1 && variant != 5 && variant != 6) {
            System.out.println("бот атакует поле " + (char) (horizontalCoordinate - 1 + 64) + verticalCoordinate);
            if (enemyField.getField()[horizontalCoordinate - 2][verticalCoordinate - 1].equals(Cell.EMPTY.value)) {
                playerObserveField.getField()[horizontalCoordinate - 2][verticalCoordinate - 1] = Cell.SHOT.value;
                enemyField.getField()[horizontalCoordinate - 2][verticalCoordinate - 1] = Cell.SHOT.value;
                System.out.println("промазал при поиске");
                return new int[]{horizontalCoordinate, verticalCoordinate, 2, 0};
            } else if (!(enemyField.getField()[horizontalCoordinate - 2][verticalCoordinate - 1].equals(Cell.SHOT.value) ||
                    enemyField.getField()[horizontalCoordinate - 2][verticalCoordinate - 1].equals(Cell.DEAD.value) ||
                    enemyField.getField()[horizontalCoordinate - 2][verticalCoordinate - 1].equals(Cell.EMPTY.value))) {
                String hit = hitShip(verticalCoordinate, horizontalCoordinate - 1, ships,
                        enemyField, playerObserveField);
                System.out.println(hit);
                if (hit.equals("убил")) {
                    System.out.println("убил при поиске");
                    int[] nextAttack = searchShip(playerObserveField.getField(), ships);
                    nextAttack[2] = 0;
                    return nextAttack;
                } else {
                    System.out.println("попал при поиске");
                    return new int[]{horizontalCoordinate, verticalCoordinate, 3, 1};
                }
            }
        }
        //снизу
        if (variant != 4 && variant != 7 && variant != 8) {
            System.out.println("бот атакует поле " + (char) (horizontalCoordinate + 1 + 64) + verticalCoordinate);
            if (enemyField.getField()[horizontalCoordinate][verticalCoordinate - 1].equals(Cell.EMPTY.value)) {
                playerObserveField.getField()[horizontalCoordinate][verticalCoordinate - 1] = Cell.SHOT.value;
                enemyField.getField()[horizontalCoordinate][verticalCoordinate - 1] = Cell.SHOT.value;
                System.out.println("промазал при поиске");
                return new int[]{horizontalCoordinate, verticalCoordinate, 2, 0};
            } else if (!(enemyField.getField()[horizontalCoordinate][verticalCoordinate - 1].equals(Cell.SHOT.value) ||
                    enemyField.getField()[horizontalCoordinate][verticalCoordinate - 1].equals(Cell.DEAD.value) ||
                    enemyField.getField()[horizontalCoordinate][verticalCoordinate - 1].equals(Cell.EMPTY.value))) {
                String hit = hitShip(verticalCoordinate, horizontalCoordinate + 1, ships,
                        enemyField, playerObserveField);
                System.out.println(hit);
                if (hit.equals("убил")) {
                    System.out.println("убил при поиске");
                    int[] nextAttack = searchShip(playerObserveField.getField(), ships);
                    nextAttack[2] = 0;
                    return nextAttack;
                } else {
                    System.out.println("попал при поиске");
                    return new int[]{horizontalCoordinate + 1, verticalCoordinate, 3, 2};
                }
            }
        }
        //справа
        if (variant != 2 && variant != 5 && variant != 7) {
            System.out.println("бот атакует поле " + (char) (horizontalCoordinate + 64) + (verticalCoordinate + 1));
            if (enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate].equals(Cell.EMPTY.value)) {
                playerObserveField.getField()[horizontalCoordinate - 1][verticalCoordinate] = Cell.SHOT.value;
                enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate] = Cell.SHOT.value;
                System.out.println("промазал при поиске");
                return new int[]{horizontalCoordinate, verticalCoordinate, 2, 0};
            } else if (!(enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate].equals(Cell.SHOT.value) ||
                    enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate].equals(Cell.DEAD.value) ||
                    enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate].equals(Cell.EMPTY.value))) {
                String hit = hitShip(
                        verticalCoordinate + 1,
                        horizontalCoordinate,
                        ships,
                        enemyField,
                        playerObserveField
                );
                System.out.println(hit);
                if (hit.equals("убил")) {
                    System.out.println("убил при поиске");
                    int[] nextAttack = searchShip(playerObserveField.getField(), ships);
                    nextAttack[2] = 0;
                    return nextAttack;
                } else {
                    System.out.println("попал при поиске");
                    return new int[]{horizontalCoordinate, verticalCoordinate + 1, 3, 3};
                }
            }
        }
        //слева
        if (variant != 3 && variant != 6 && variant != 8) {
            System.out.println("бот атакует поле " + (char) (horizontalCoordinate + 64) + (verticalCoordinate - 1));
            if (enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 2].equals(Cell.EMPTY.value)) {
                playerObserveField.getField()[horizontalCoordinate - 1][verticalCoordinate - 2] = Cell.SHOT.value;
                enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 2] = Cell.SHOT.value;
                System.out.println("промазал при поиске");
                return new int[]{horizontalCoordinate, verticalCoordinate, 2, 0};
            } else if (!(enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 2].equals(Cell.SHOT.value) ||
                    enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 2].equals(Cell.DEAD.value) ||
                    enemyField.getField()[horizontalCoordinate - 1][verticalCoordinate - 2].equals(Cell.EMPTY.value))) {
                String hit = hitShip(
                        verticalCoordinate - 1,
                        horizontalCoordinate,
                        ships,
                        enemyField,
                        playerObserveField
                );
                if (hit.equals("убил")) {
                    System.out.println("убил при поиске");
                    int[] nextAttack = searchShip(playerObserveField.getField(), ships);
                    nextAttack[2] = 0;
                    return nextAttack;
                } else {
                    System.out.println("попал при поиске");
                    return new int[]{horizontalCoordinate, verticalCoordinate - 1, 3, 4};
                }
            }
        }
        return new int[]{0, 0, 0, 0};


    }

    public int[] searchShip(String[][] playerObserveField, ArrayList<Ship> ships) {
        int number = 0;
        for (Ship ship : ships) {
            if (ship.getNumberOfDeck() > number) {
                number = ship.getNumberOfDeck();
            }
        }
        int horizontalCoordinate1 = 1;
        int horizontalCoordinate2 = 1 + (number - 1);
        int verticalCoordinate1 = 1;
        int verticalCoordinate2 = verticalCoordinate1;
        //поиск вертикальных кораблей
        for (int i = 0; i < 16 - (number - 1); i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < number; k++) {
                    if (i + k >= horizontalCoordinate1 - 1 && i + k <= horizontalCoordinate2 - 1 && j == verticalCoordinate1 - 1 &&
                            !playerObserveField[i + k][j].equals(Cell.EMPTY.value)) {
                        break;
                    }
                    if (k == number - 1) {
                        return new int[]{horizontalCoordinate1 + (number / 2), verticalCoordinate1, 0, 0};
                    }
                }
                verticalCoordinate1 = verticalCoordinate1 % 16 + 1;
                verticalCoordinate2 = verticalCoordinate2 % 16 + 1;
            }
            horizontalCoordinate1 = horizontalCoordinate1 % 16 + 1;
            horizontalCoordinate2 = horizontalCoordinate2 % 16 + 1;
        }
        //поиск горизонтальных кораблей
        horizontalCoordinate1 = 1;
        horizontalCoordinate2 = horizontalCoordinate1;
        verticalCoordinate1 = 1;
        verticalCoordinate2 = 1 + (number - 1);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16 - (number - 1); j++) {
                for (int k = 0; k < number; k++) {
                    if (i == horizontalCoordinate1 - 1 && j + k >= verticalCoordinate1 - 1 && j + k <= verticalCoordinate1 &&
                            !playerObserveField[i][j].equals(Cell.EMPTY.value)) {
                        break;
                    }
                    if (k == number - 1) {
                        return new int[]{horizontalCoordinate1, verticalCoordinate1 + (number / 2), 0, 0};
                    }
                }
                verticalCoordinate1 = verticalCoordinate1 % 16 + 1;
                verticalCoordinate2 = verticalCoordinate2 % 16 + 1;
            }
            horizontalCoordinate1 = horizontalCoordinate1 % 16 + 1;
            horizontalCoordinate2 = horizontalCoordinate2 % 16 + 1;
        }
        return new int[]{0, 0, 0, 0};
    }

    public String hitShip(
            int verticalCoordinateAttac,
            int horizontalCoordinateAttac,
            ArrayList<Ship> ships,
            Field enemyField,
            Field playerObserveField
    ) {
        for (Ship ship : ships) {
            if (ship.getCoordinateOne().getY() <= horizontalCoordinateAttac &&
                    ship.getCoordinateTwo().getY() >= horizontalCoordinateAttac &&
                    ship.getCoordinateOne().getX() <= verticalCoordinateAttac &&
                    ship.getCoordinateTwo().getX() >= verticalCoordinateAttac) {
                enemyField.getField()[horizontalCoordinateAttac - 1][verticalCoordinateAttac - 1] = Cell.DEAD.value;
                playerObserveField.getField()[horizontalCoordinateAttac - 1][verticalCoordinateAttac - 1] = Cell.DEAD.value;
                ship.setHp(ship.getHp() - 1);
                if (ship.getHp() == 0) {
                    int variant = bordresWithField(horizontalCoordinateAttac, verticalCoordinateAttac);
                    killShip(ship, variant, enemyField);
                    killShip(ship, variant, playerObserveField);
                    ships.remove(ship);
                    return "убил";
                }
                return "попал";
            }

        }
        return "ошибка3"; //СОЗДАТЬ КЛАСС СТРОК-КОНСТАНТ
    }

    public void killShip(Ship ship, int variant, Field field) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                switch (variant) {
                    case (0):
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX())) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (1):
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 1) && j <= (ship.getCoordinateTwo().getX())) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (2):
                        if (i >= (ship.getCoordinateOne().getY() - 1) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX())) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (3):
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY() - 1) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX())) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (4):
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX() - 1)) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (5):
                        if (i >= (ship.getCoordinateOne().getY() - 1) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 1) && j <= (ship.getCoordinateTwo().getX())) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (6):
                        if (i >= (ship.getCoordinateOne().getY() - 1) && i <= (ship.getCoordinateTwo().getY()) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX() - 1)) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (7):
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY() - 1) &&
                                j >= (ship.getCoordinateOne().getX() - 1) && j <= (ship.getCoordinateTwo().getX())) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }
                    case (8):
                        if (i >= (ship.getCoordinateOne().getY() - 2) && i <= (ship.getCoordinateTwo().getY() - 1) &&
                                j >= (ship.getCoordinateOne().getX() - 2) && j <= (ship.getCoordinateTwo().getX() - 1)) {
                            if (i >= ship.getCoordinateOne().getY() - 1 && i <= ship.getCoordinateTwo().getY() - 1 &&
                                    j >= ship.getCoordinateOne().getX() - 1 && j <= ship.getCoordinateTwo().getX() - 1) {
                                field.getField()[i][j] = Cell.DEAD.value;
                            } else field.getField()[i][j] = Cell.SHOT.value;
                        }

                }
            }

        }

    }


    public int bordresWithField(int horizontalCoordinat, int verticalCoordinat) {
        //1.Случай верхней границы
        if (horizontalCoordinat == 1) {
            //5.Случай левого верхнего угла
            if (verticalCoordinat == 1) {
                return 5;
            }
            //6.Случай правого верхнего угла
            if (verticalCoordinat == 16) {
                return 6;
            }

            return 1;
        }


        //2.Случай левой границы
        if (verticalCoordinat == 16) {
            //7.Случай нижнего левого угла
            if (horizontalCoordinat == 16) {
                return 7;
            }
            return 2;
        }
        //3.Случай правой границы
        if (verticalCoordinat == 1) {
            //8.Случай нижнего правного угла
            if (horizontalCoordinat == 16) {
                return 8;
            }
            return 3;
        }
        //4.Случай нижней границы
        if (horizontalCoordinat == 16) {
            return 4;
        }
        //0.Случай непопадания в границы
        return 0;

    }


    public int getVerticalCoordinate(String coordinats) {
        //циферные координаты
        String horizontalCoordinat0 = coordinats.replaceAll("[A-P]", "");
        return Integer.parseInt(horizontalCoordinat0);
    }

    public int getHorizontalCoordinate(String coordinats) {
        //буквенные координаты
        String verticalCoordinat0 = coordinats.replaceAll("[1-9]", "");
        return verticalCoordinat0.charAt(0) - 64;
    }

    public boolean checkRightWriting(String coordinats) {
        boolean trueWrite = coordinats.matches("([A-P])([1-9])([0-6]?)");
        if (trueWrite) {
            return true;
        } else System.out.println("Неправильно заданы координаты");
        return false;
    }

}
