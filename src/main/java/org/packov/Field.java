package org.packov;


public class Field {
    String[][] field;
public Field(String[][] field){
    this.field = field;
}

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

    //Выводим поле
    public void showField(){
        for (int i = 0; i < 17; i++) {
            System.out.printf("\033[0;33m" + "%3s" + "\033[0m" + "|", i);
        }
        String[] horizontalCoordinates = {"A", "B", "C", "D", "E", "F", "G", "H","I","J","K","L","M","N","O","P" };
        for (int i = 0; i < 16; i++) {
            System.out.println();
            System.out.printf("\033[0;33m" + "%2s" + "\033[0m" + " | ",horizontalCoordinates[i]);
            for (int j = 0; j < 16; j++) {
                System.out.print(field[i][j] + " | ");
            }
        }
        System.out.println();


    }

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
                System.out.print(playerField.field[i][j] + " | ");
            }
            System.out.print("  ");
            System.out.printf("\033[0;33m" + "%2s" + "\033[0m" + " | ",horizontalCoordinates[i]);
            for (int j = 0; j < 16; j++) {
                System.out.print(playerObserveField.field[i][j] + " | ");
            }
        }
        System.out.println();



    }


}
