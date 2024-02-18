package org.packov;

import java.util.Scanner;

public class Player {
    String name;

    public Player(){
        this.name = getNameFromConsole();
    }
    private static String getNameFromConsole(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите имя:");
        return scanner.next();
    }






}
