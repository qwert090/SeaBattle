package org.packov.utils;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inNotNullString(){
        while (true){
            String str = scanner.nextLine();
            if(!str.isEmpty()){
                return str;
            }else {
                System.out.println("Строка не должна быть пустой");
            }
        }
    }

    public static String inString(){
        return scanner.nextLine();
    }
}
