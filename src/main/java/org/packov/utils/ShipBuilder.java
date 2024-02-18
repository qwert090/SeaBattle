package org.packov.utils;

import org.packov.entity.Field;
import org.packov.entity.Point;
import org.packov.entity.Ship;
import org.packov.enums.FieldBoundary;

import java.util.ArrayList;
import java.util.Random;

public class ShipBuilder {

    public static Ship build(Field field, int numberOfDeck, boolean isAutoCreating) {
        if (isAutoCreating) {
            return autoBuild(field.getField(), numberOfDeck);
        }else {
            return manualBuild(field.getField(),numberOfDeck);
        }
    }

    private static Ship manualBuild(String[][] field, int numberOfDeck) {
        Ship ship = new Ship();
        while (true) {
            ship.setNumberOfDeck(numberOfDeck);
            ship.setHp(numberOfDeck);

            System.out.print("Введите координаты нового " + numberOfDeck + "-ти палубного корабля :");
            String coordinats = InputUtil.inNotNullString();
            coordinats = oneDeckShip(coordinats);

            if (!ShipValidator.checkRightWriting(coordinats)) {
                System.out.println("Неправильно заданы координаты!");
                continue;
            }
            
            ArrayList<Integer> horizontalCoordinats = getHorizontalCoordinates(coordinats);
            ArrayList<Integer> verticalCoordinats = getVerticalCoordinates(coordinats);
            Point coordinateOne = new Point(verticalCoordinats.get(0), horizontalCoordinats.get(0));
            Point coordinateTwo = new Point(verticalCoordinats.get(1), horizontalCoordinats.get(1));
            
            if (ShipValidator.checkRightCoordinates(coordinateOne, coordinateTwo, numberOfDeck)) {
                System.out.println("Величина шестипалубного корабля задана неправильно");
                continue;
            }

            FieldBoundary fieldBoundary = bordresWithField(coordinateOne, coordinateTwo);
            ship.setFieldBoundary(fieldBoundary);
            
            if (!ShipValidator.isWithoutConflicts(fieldBoundary, field, coordinateOne, coordinateTwo)) {
                System.out.println("В окрестности заданного поля уже есть корабль");
                continue;
            }

            ship.setCoordinateOne(coordinateOne);
            ship.setCoordinateTwo(coordinateTwo);
            break;
        }
        
        return ship;
    }

    private static Ship autoBuild(String[][] field, int numberOfDeck) {
        Ship ship = new Ship();

        ship.setNumberOfDeck(numberOfDeck);
        ship.setHp(numberOfDeck);
        
        Random random = new Random();
        int orientation = 1 + random.nextInt(2);

        Point coordinateOne;
        Point coordinateTwo;
        
        if (orientation == 1) { 
            coordinateOne = 
                    new Point(random.nextInt(15) + 1, random.nextInt(15 - (numberOfDeck - 1)) + 1);
            coordinateTwo = 
                    new Point(coordinateOne.getX(), coordinateOne.getY() + (numberOfDeck - 1));

        } else {
            coordinateOne =
                    new Point(random.nextInt(15 - (numberOfDeck - 1)) + 1, random.nextInt(15) + 1);
            coordinateTwo =
                    new Point(coordinateOne.getX() + (numberOfDeck - 1), coordinateOne.getY());
        }

        outerLoop:
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {

                FieldBoundary fieldBoundary = bordresWithField(coordinateOne, coordinateTwo);
                ship.setFieldBoundary(fieldBoundary);
                if (ShipValidator.checkRightCoordinates(coordinateOne, coordinateTwo, numberOfDeck)) {
                    coordinateOne.setX(coordinateOne.getX() % 16 + 1);
                    coordinateTwo.setX(coordinateTwo.getX() % 16 + 1);
                    continue;
                }
                if (ShipValidator.isWithoutConflicts(fieldBoundary, field, coordinateOne, coordinateTwo)) {
                    break outerLoop;
                } else {
                    coordinateOne.setX(coordinateOne.getX() % 16 + 1);
                    coordinateTwo.setX(coordinateTwo.getX() % 16 + 1);
                }
            }
            coordinateOne.setY(coordinateOne.getY() % 16 + 1);
            coordinateTwo.setY(coordinateTwo.getY() % 16 + 1);
        }

        ship.setCoordinateOne(coordinateOne);
        ship.setCoordinateTwo(coordinateTwo);

        return ship;
    }
    //TODO удалить поле бота
    private static ArrayList<Integer> getVerticalCoordinates(String coordinates) {
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


    private static ArrayList<Integer> getHorizontalCoordinates(String coordinates) {
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

    private static String oneDeckShip(String coordinates) {
        if (coordinates.matches("([A-P])([1-9])([0-6]?)")) {
            coordinates = coordinates + "-" + coordinates;
            return coordinates;
        }
        return coordinates;
    }

    private static FieldBoundary bordresWithField(Point coordinateOne, Point coordinateTwo) {
        //1.Случай верхней границы
        if (coordinateOne.getY() == 1 || coordinateTwo.getY() == 1) {
            //5.Случай левого верхнего угла
            if (coordinateOne.getX() == 1 || coordinateTwo.getX() == 1) {
                return FieldBoundary.TOP_LEFT;
            }
            //6.Случай правого верхнего угла
            if (coordinateOne.getX() == 16 || coordinateTwo.getX() == 16) {
                return FieldBoundary.TOP_RIGHT;
            }
            return FieldBoundary.TOP;
        }
        //2.Случай левой границы
        if (coordinateOne.getX() == 1 || coordinateTwo.getX() == 1) {
            //7.Случай нижнего левого угла
            if (coordinateTwo.getY() == 16 || coordinateOne.getY() == 16) {
                return FieldBoundary.BOTTOM_LEFT;
            }
            return FieldBoundary.LEFT;

        }
        //3.Случай правой границы
        if (coordinateOne.getX() == 16 || coordinateTwo.getX() == 16) {
            //8.Случай нижнего правного угла
            if (coordinateOne.getY() == 16 || coordinateTwo.getY() == 16) {
                return FieldBoundary.BOTTOM_RIGHT;
            }
            return FieldBoundary.RIGHT;

        }
        //4.Случай нижней границы
        if (coordinateOne.getY() == 16 || coordinateTwo.getY() == 16) {
            return FieldBoundary.BOTTOM;
        }
        //0.Случай непопадания в границы
        return FieldBoundary.NON;

    }
}
