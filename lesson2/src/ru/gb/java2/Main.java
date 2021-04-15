package ru.gb.java2;

public class Main {

    public static void main(String[] args) {
	    String [][] numbers = {
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
        };

	    int sum = 0;

        //Можно обработать только одно исключение RuntimeException, либо обработать оба исключения
	    // Если нужно вывести только сообщение об эксшепшене, то доставточно вот такого кода:
        // try {
        //            sum = sumStringArray(numbers);
        //        } catch (RuntimeException e) {
        //	        e.printStackTrace();
        //        }

        //Но по заданию надо обработать оба, так и сделаю
        //Прошу дать комментарий как лучше сделать и нужно ли делать printStackTrace()...
        //я закомментил
        try {
            sum = sumStringArray(numbers);
        } catch (MyArraySizeExсeption e) {
            System.out.println("Обрабатываем MyArraySizeExсeption...");
            System.out.println("Размер массива не совпадет в заданным в задании!");
            //Вот тут можно сделать вместо exit(1)
	        //e.printStackTrace();
            return;
        } catch (MyArrayDataException e) {
            System.out.println("Обрабатываем MyArrayDataException...");
            System.out.printf("В массиве по координатам (%d, %d) расположено не корректное значение %s\n",
                    e.getI(), e.getJ(), numbers[e.getI()][e.getJ()]);
            //Вот тут можно сделать вместо exit(2) или что-то такое...
            //e.printStackTrace();
            return;
        }
        System.out.println("Сумма: " + sum);
    }

    public static int sumStringArray(String [][] numbers) {
        if (numbers.length == 4) {
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i].length != 4)
                    throw new MyArraySizeExсeption();
            }
        } else {
                throw new MyArraySizeExсeption();
        }
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                try {
                    sum += Integer.parseInt(numbers[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }
        return sum;
    }
}
