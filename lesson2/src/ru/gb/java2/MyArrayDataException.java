package ru.gb.java2;

public class MyArrayDataException extends RuntimeException {
    private int i;
    private int j;

    public MyArrayDataException(int i, int j) {
        super("Элемент с координатами ("+ i +","+ j +")  преобразовать невозможно!");
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
