package ru.gb.java2;

public class Treadmill implements IOvercome {
    private int distance;

    public Treadmill(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean overcome(Object someone) {
        IRunnable s = (IRunnable) someone;
        if (s.run(this.distance)) {
            System.out.printf("Дистанция %s пройдена!\n", this.distance);
            return true;
        }
        return false;
    }
}
