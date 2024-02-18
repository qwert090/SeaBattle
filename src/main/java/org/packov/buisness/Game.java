package org.packov.buisness;

import org.packov.entity.Field;
import org.packov.entity.Player;
import org.packov.entity.Ship;
import org.packov.utils.InputUtil;
import org.packov.utils.ShipBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    ArrayList<Ship> playerOneShips;
    ArrayList<Ship> playerTwoShips;
    Field playerOneField;
    Field playerTwoField;
    Field playerOneObserveField;
    Field playerTwoObserveField;
    Battle battle;

    public Game() {
        playerOneShips = new ArrayList<>();
        playerTwoShips = new ArrayList<>();
        playerOneField = new Field(Field.createField());
        playerTwoField = new Field(Field.createField());
        playerOneObserveField = new Field(Field.createField());
        playerTwoObserveField = new Field(Field.createField());
        battle = new Battle();
    }
    public void play(){
        outerLoop:
        while (true) {
            System.out.print("""
                    Выберите режим игры :
                    Одиночный(1), На двоих(2)
                    """);
            switch (InputUtil.inNotNullString()) {
                case "1" -> {
                    System.out.print("Введите имя игрока: ");
                    String playerName = InputUtil.inNotNullString();
                    Player player = new Player(playerName);
                    playWithBot(player);
                    break outerLoop;
                }
                case "2" -> {
                    System.out.print("Введите имя первого игрока: ");
                    String playerName = InputUtil.inNotNullString();
                    Player playerOne = new Player(playerName);
                    System.out.print("Введите имя второго игрока: ");
                    playerName = InputUtil.inNotNullString();
                    Player playerTwo = new Player(playerName);
                    playClassic(playerOne, playerTwo);
                    break outerLoop;
                }
                default -> System.out.println("Пожалуйста, введите 1 или 2");
            }
        }
    }

    public void playClassic(Player playerOne, Player playerTwo) {
        System.out.println("Начинаем игру");
        //первый игрок заполняет поле
        System.out.println(playerOne.name() + " заполните поле");
        playerOneShips = disposalShips(playerOne, playerOneField);
        System.out.println(playerTwo.name() + " продолжите для заполнения поля");
        Battle.pressEnter();
        playerTwoShips = disposalShips(playerTwo, playerTwoField);
        int step = 0;
        while (true) {
            String result;
            if (step % 2 + 1 == 1) {
                System.out.println(playerOne.name() + " продолжите для начала атаки");
                Battle.pressEnter();
                result = battle.attack(playerOneField, playerOneObserveField, playerTwoField, playerTwoShips, playerOne);
                step++;
            } else if (step % 2 + 1 == 2) {
                System.out.println(playerOne.name() + " продолжите для начала атаки");
                Battle.pressEnter();
                result = battle.attack(playerTwoField, playerTwoObserveField, playerOneField, playerOneShips, playerTwo);
                step++;
            } else {
                System.out.println("ошибка1");
                break;
            }
            if (result.equals("победил")) {
                break;
            }
            System.out.println("результат");
            System.out.println(result);
        }
    }

    public void playWithBot(Player player){
        System.out.println("Начинаем игру с ботом");
        // игрок заполняет поле
        playerOneShips = disposalShips(player, playerOneField);
        playerTwoShips = Game.autoCreateShips(playerTwoField);
        int step = 0;
        int[] nextAttack = {0,0,0,0};
        while (true) {
            String result = " ";
            if (step % 2 + 1 == 1) {
                result = battle.attack(playerOneField, playerOneObserveField, playerTwoField, playerTwoShips, player);
                step++;
            } else if (step % 2 + 1 == 2) {
                nextAttack = battle.autoAttack(playerTwoObserveField, playerOneField, playerOneShips,nextAttack);
                step++;
            }
            System.out.println("результат");
            System.out.println(result);
            if (result.equals("победил")) {
                break;
            }
        }

    }

    public static ArrayList<Ship> disposalShips(Player player, Field playerField) {
        while (true) {
            ArrayList<Ship> ships;
            System.out.println(player.name() + " каким способом предпочтете расставить корабли?\nСамостоятельно(1) или Автоматически(2)");
            String disposal = InputUtil.inNotNullString();
            if (disposal.equals("1")) {
                ships = Game.createShipsYourSelf(playerField);
                return ships;
            } else if (disposal.equals("2")) {
                ships = Game.autoCreateShips(playerField);
                System.out.println(playerField.toString());
                return ships;
            }
            System.out.println("Введите 1 или 2");
        }
    }

    public static ArrayList<Ship> autoCreateShips(Field field) {
        ArrayList<Ship> ships = new ArrayList<>();

        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7 - i; j++) {
                Ship ship = ShipBuilder.build(field, i, true);
                ships.add(ship);
                field.placeShip(ship);
            }
        }

        field.cleanField();
        return ships;
    }

    public static ArrayList<Ship> createShipsYourSelf(Field field) {
        int[] deckShip = {6, 5, 4, 3, 2, 1};
        ArrayList<Ship> ships = new ArrayList<>();
        while (true) {
            System.out.println("Введите какого размера будет корабль(1-6) :");
            String number = InputUtil.inNotNullString();
            if (Arrays.equals(deckShip, new int[]{0, 0, 0, 0, 0, 0})) {
                return ships;
            }

            try {
                int size = Integer.parseInt(number);
                if(size < 1 || size > 6){
                    System.out.println("Неправильно введен размер корабля");
                }
                else{
                    if (deckShip[size - 1] != 0) {
                        Ship ship = ShipBuilder.build(field, size, false);
                        ships.add(ship);
                        field.placeShip(ship);
                        deckShip[size - 1]--;
                        System.out.println(field);
                    } else {
                        System.out.println("Таких кораблей не осталось");
                    }
                }
            }catch (NumberFormatException numberFormatException){
                System.out.println("Введите число");
            }
        }
    }
}
