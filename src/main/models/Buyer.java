package main.models;

public class Buyer {

    private String name;
    private double money;

    public Buyer(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public void decreaseMoney(double decreaseWith) {
        money -= decreaseWith;
    }

    public String getInfo() {
        String moneyString = String.format("%.2f", money);

        return "Ennyi pénzed van: "
                + moneyString;
    }

}
