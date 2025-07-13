package entity;

public class DefaultPlayer {
    private String name;
    private int money;
    private int position;

    public void DefaultPlayer(String name, int initialMoney) {
        this.name = name;
        this.money = initialMoney;
        this.position = 0;
    }

    public String  getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public int getPosition() {
        return this.position;
    }
}
