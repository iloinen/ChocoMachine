package main.models;

public class ChocoMachine {

    private Chocolate[] chocolates;
    private boolean isOutOfService;

    public ChocoMachine(Chocolate[] chocolates) {
        this.chocolates = chocolates;
    }

    public void serveChocolate(int chocoIndex, int amount) {
        chocolates[chocoIndex].decreaseAvailablePieces(amount);
    }

    public void refillChocolate(int chocoIndex, int refillWith) {
        chocolates[chocoIndex].increaseAvailablePieces(refillWith);
    }

    public boolean doesChocolateExist(int chocoIndex) {
        return chocoIndex >= 0 && chocoIndex < chocolates.length;
    }

    public boolean canServeChocolate(int chocoIndex, int amountToBuy) {
        return chocolates[chocoIndex].getAmount() >= amountToBuy;
    }

    public Chocolate getChocolate(int chocoIndex) {
        return chocolates[chocoIndex];
    }

    public Chocolate[] getChocolates() {
        return chocolates;
    }

    public boolean isOutOfService() {
        return isOutOfService;
    }

    public void setOutOfService(boolean outOfService) {
        isOutOfService = outOfService;
    }

    public String getInfo() {
        return "A masina üzemel.\n"
                + "Elérhető nyalánkságok:\n"
                + chocolatesInfo();
    }

    private String chocolatesInfo() {
        String chocolatesString = "";

        for (int i = 0; i < chocolates.length; i++) {
            chocolatesString += "\t" + (i + 1) + ". " + chocolates[i].getInfo();

            if (i < chocolates.length - 1) {
                chocolatesString += "\n";
            }
        }

        return chocolatesString;
    }

}
