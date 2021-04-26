package ru.gb.java2;

public class Main {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;


    public static void main(String[] args) {
        methodOne();
        methodTwo();
    }

    public static void methodOne() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long timeMillis = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - timeMillis);

    }

    public static void methodTwo() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];

        long timeMillis = System.currentTimeMillis();

        System.arraycopy(arr, 0,arr1,0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float)(arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < arr2.length; i++) {
                //тут не понятно должны ли массивы быть с одинаковыми значениями... Если с одинаковыми,
                // то на надо вносить правку  + HALF
                arr2[i] = (float)(arr2[i] * Math.sin(0.2f + (i+HALF) / 5) * Math.cos(0.2f + (i+HALF) / 5)
                        * Math.cos(0.4f + (i+HALF) / 2));
            }

        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.out.println(System.currentTimeMillis() - timeMillis);
    }
}
