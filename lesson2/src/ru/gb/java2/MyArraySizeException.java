package ru.gb.java2;

public class MyArraySizeException extends RuntimeException {

    public MyArraySizeException() {
        super("Текущий размер массива не совпадает с заданным!");
    }

}
