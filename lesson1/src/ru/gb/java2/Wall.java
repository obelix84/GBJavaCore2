package ru.gb.java2;

public class Wall implements IOvercome{

    private int height;

    public Wall(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean overcome(Object someone) {
        IJumpable s = (IJumpable) someone;
        if (s.jump(this.height)) {
            System.out.printf("Стена высотой %s перепрыгнута!\n", this.height);
            return true;
        }
        return false;
    }
}
