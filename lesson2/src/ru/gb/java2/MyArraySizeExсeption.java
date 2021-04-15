package ru.gb.java2;

public class MyArraySizeExсeption extends RuntimeException {

    public MyArraySizeExсeption() {
        super("Текущий размер массива не совпадает с заданным!");
    }

}
