package org.packov.utils;

import org.packov.entity.Field;

//TODO вынести строки в константы
public class FieldUtil {
    public static void showTwoFields(Field playerField, Field playerObserveField){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 17; j++) {
                System.out.printf("\033[0;33m" + "%2s " + "\033[0m" + "|", j);
            }
            System.out.print("   ");
        }

        String[] horizontalCoordinates = {"A", "B", "C", "D", "E", "F", "G", "H","I","J","K","L","M","N","O","P" };
        for (int i = 0; i < 16; i++) {
            System.out.println();
            System.out.printf("\033[0;33m" + "%2s" + "\033[0m" + " | ",horizontalCoordinates[i]);
            for (int j = 0; j < 16; j++) {
                System.out.print(playerField.getField()[i][j] + " | ");
            }
            System.out.print("  ");
            System.out.printf("\033[0;33m" + "%2s" + "\033[0m" + " | ",horizontalCoordinates[i]);
            for (int j = 0; j < 16; j++) {
                System.out.print(playerObserveField.getField()[i][j] + " | ");
            }
        }
        System.out.println();
    }
}
