package ru.gb.java2;

public class Robot implements IRunnable, IJumpable {

    private String serialNumber;
    private int energyRun;
    private int energyJump;

    public Robot(String serialNumber, int energyRun, int energyJump) {
        this.serialNumber = serialNumber;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean run(int distance) {
        if (this.energyRun < distance)
            return false;
        this.energyRun -= distance;
        System.out.printf("Робот с серийным номером %s побежал!\n", this.serialNumber);
        return true;
    }

    @Override
    public boolean jump(int height) {
        if (this.energyJump < height)
            return false;
        this.energyJump -= height;
        System.out.printf("Робот с серийным номером %s подпрыгнул!\n", this.serialNumber);
        return true;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "serialNumber='" + serialNumber + '\'' +
                ", energyRun=" + energyRun +
                ", energyJump=" + energyJump +
                '}';
    }
}
