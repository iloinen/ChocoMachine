package main.models;

public class Chocolate {

    private String name;
    private double price;
    private int amount;

    public Chocolate(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public void increaseAvailablePieces(int increaseWith) {
        amount += increaseWith;
    }

    public void decreaseAvailablePieces(int decreaseWith) {
        amount -= decreaseWith;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getInfo() {
        String priceString = String.format("%.2f", price);

        return name
                + " - ára: " + priceString
                + " (elérhető: " + amount + " darab)";
    }

}
