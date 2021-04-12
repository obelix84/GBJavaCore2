package ru.gb.java2;

public class Cat implements IRunnable, IJumpable{

    private String name;
    private int energyRun;
    private int energyJump;

    public Cat(String name, int energyRun, int energyJump) {
        this.name = name;
        this.energyRun = energyRun;
        this.energyJump = energyJump;
    }

    public int getEnergyRun() {
        return energyRun;
    }

    public void setEnergyRun(int energyRun) {
        this.energyRun = energyRun;
    }

    public int getEnergyJump() {
        return energyJump;
    }

    public void setEnergyJump(int energyJump) {
        this.energyJump = energyJump;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", energyRun=" + energyRun +
                ", energyJump=" + energyJump +
                '}';
    }

    @Override
    public boolean run(int distance) {
        if (this.energyRun < distance)
            return false;
        this.energyRun -= distance;
        System.out.printf("Кот по имени %s побежал!\n", this.name);
        return true;
    }

    @Override
    public boolean jump(int height) {
        if (this.energyJump < height)
            return false;
        this.energyJump -= height;
        System.out.printf("Кот по имени %s подпрыгнул!\n", this.name);
        return true;
    }
}
