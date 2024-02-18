package org.packov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {


    ArrayList<Ship> ships1;
    ArrayList<Ship> ships2;
    //Создаем поля
    Field playerField1;
    Field playerField2;
    Field playerObserveField1;
    Field playerObserveField2;
    Battle battle;

    public Game() {
        ships1 = new ArrayList<>();
        ships2 = new ArrayList<>();
        //Создаем поля
        playerField1 = new Field(Field.createField());
        playerField2 = new Field(Field.createField());
        playerObserveField1 = new Field(Field.createField());
        playerObserveField2 = new Field(Field.createField());
        battle = new Battle();
    }
    public void play(){
        Scanner scanner = new Scanner(System.in);
        outerLoop:
        while (true) {
            System.out.print("""
                    Выберите режим игры :
                    Одиночный(1), На двоих(2)
                    Зайти под именем администратора(0)
                    """);
            switch (scanner.next()) {
                case "0" -> System.out.println("Введите пароль");

                case "1" -> {
                    Player player1 = new Player();
                    playWithBot(player1);
                    break outerLoop;

                }
                case "2" -> {
                    System.out.print("Первый игрок ");
                    Player player1 = new Player();
                    System.out.print("Второй игрок ");
                    Player player2 = new Player();
                    playClassic(player1,player2);
                    break outerLoop;
                }
                default -> System.out.println("Пожалуйста введите 1, 2 или 3 ");

            }
        }
    }

    public void playClassic(Player player1, Player player2) {
        System.out.println("Начинаем игру");
        //первый игрок заполняет поле
        System.out.println(player1.name + " заполните поле");
        ships1 = disposalShips(player1, playerField1);
        System.out.println(player2.name + " продолжите для заполнения поля");
        Battle.pressEnter();
        ships2 = disposalShips(player2, playerField2);
        int step = 0;
        while (true) {
            String result;
            if (step % 2 + 1 == 1) {
                System.out.println(player1.name + " продолжите для начала атаки");
                Battle.pressEnter();
                result = battle.attack(playerField1,playerObserveField1, playerField2, ships2, player1);
                step++;
            } else if (step % 2 + 1 == 2) {
                System.out.println(player1.name + " продолжите для начала атаки");
                Battle.pressEnter();
                result = battle.attack(playerField2,playerObserveField2, playerField1, ships1, player2);
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
        ships1 = disposalShips(player, playerField1);
        ships2 = Game.autoCreateShips(playerField2);
        System.out.println("поле бота");
        Field.showTwoFields(playerField2,playerObserveField2);
        int step = 0;
        int[] nextAttack = {0,0,0,0};
        while (true) {
            String result = " ";
            if (step % 2 + 1 == 1) {
                result = battle.attack(playerField1,playerObserveField1, playerField2, ships2, player);
                step++;
            } else if (step % 2 + 1 == 2) {
                nextAttack = battle.autoAttack(playerObserveField2, playerField1, ships1,nextAttack);
                step++;
            } else {
                System.out.println("ошибка2");
                break;
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
            Scanner scanner = new Scanner(System.in);
            ArrayList<Ship> ships;
            System.out.println(player.name + " каким способом предпочтете расставить корабли?\nСамостоятельно(1) или Автоматически(2)");
            String disposal = scanner.next();
            if (disposal.equals("1")) {
                ships = Game.createShipsYourSelf(playerField);
                return ships;
            } else if (disposal.equals("2")) {
                ships = Game.autoCreateShips(playerField);
                playerField.showField();
                return ships;
            }
            System.out.println("Введите 1 или 2");

        }

    }

    public static ArrayList<Ship> autoCreateShips(Field field) {
        ArrayList<Ship> ships = new ArrayList<>();
        int[] deckShip = {6, 5, 4, 3, 2, 1};
        for (int i = 0; i < 21; i++) {
            if (deckShip[5] != 0) {
                Ship ship = new Ship(field, 6, 2);
                ships.add(ship);
                deckShip[5]--;
            } else if (deckShip[4] != 0) {
                Ship ship = new Ship(field, 5, 2);
                ships.add(ship);
                deckShip[4]--;
            } else if (deckShip[3] != 0) {
                Ship ship = new Ship(field, 4, 2);
                ships.add(ship);
                deckShip[3]--;
            } else if (deckShip[2] != 0) {
                Ship ship = new Ship(field, 3, 2);
                ships.add(ship);
                deckShip[2]--;
            } else if (deckShip[1] != 0) {
                Ship ship = new Ship(field, 2, 2);
                ships.add(ship);
                deckShip[1]--;
            } else if (deckShip[0] != 0) {
                Ship ship = new Ship(field, 1, 2);
                ships.add(ship);
                deckShip[0]--;
            }
        }
        field.cleanField();
        return ships;
    }

    public static ArrayList<Ship> createShipsYourSelf(Field field) {
        Scanner scanner = new Scanner(System.in);
        int[] deckShip = {6, 5, 4, 3, 2, 1};
        ArrayList<Ship> ships = new ArrayList<>();
        while (true) {
            System.out.println("Введите какого размера будет корабль(1-6) :");
            String number = scanner.next();
            if (Arrays.equals(deckShip, new int[]{0, 0, 0, 0, 0, 0})) {
                return ships;

            }

            switch (number) {
                case "1":
                    if (deckShip[0] != 0) {
                        Ship ship = new Ship(field, 1, 1);
                        ships.add(ship);
                        deckShip[0]--;
                        field.showField();
                    } else {
                        System.out.println("таких кораблей не осталось");
                    }
                    break;
                case "2":
                    if (deckShip[1] != 0) {
                        Ship ship = new Ship(field, 2, 1);
                        ships.add(ship);
                        deckShip[1]--;
                        field.showField();
                    } else {
                        System.out.println("таких кораблей не осталось");
                    }

                    break;
                case "3":
                    if (deckShip[2] != 0) {
                        Ship ship = new Ship(field, 3, 1);
                        ships.add(ship);
                        deckShip[2]--;
                        field.showField();
                    } else {
                        System.out.println("таких кораблей не осталось");
                    }
                    break;
                case "4":
                    if (deckShip[3] != 0) {
                        Ship ship = new Ship(field, 4, 1);
                        ships.add(ship);
                        deckShip[3]--;
                        field.showField();
                    } else {
                        System.out.println("таких кораблей не осталось");
                    }
                    break;
                case "5":
                    if (deckShip[4] != 0) {
                        Ship ship = new Ship(field, 5, 1);
                        ships.add(ship);
                        deckShip[4]--;
                        field.showField();
                    } else {
                        System.out.println("таких кораблей не осталось");
                    }
                    break;
                case "6":
                    if (deckShip[5] != 0) {
                        Ship ship = new Ship(field, 6, 1);
                        ships.add(ship);
                        deckShip[5]--;
                        field.showField();
                    } else {
                        System.out.println("таких кораблей не осталось");
                    }
                    break;
                default:
                    System.out.println("Неправильно введен размер корабля");

            }

        }
    }
}
