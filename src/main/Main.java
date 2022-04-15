package main;

import main.models.Buyer;
import main.models.ChocoMachine;
import main.models.Chocolate;

import java.util.Scanner;

/**
 * Running the program:
 *      sets cheapestPrice
 *      sets machine
 *      asks the user for his/her name, and sets buyer with the name (and gives random amount of money)
 *      starts the loop (that runs forever!)
 *      checks if the machine is working or not
 *          if not, then breaks the loop, and the program exits
 *      randomly refills the chocolates in the machine
 *      prints the info of the buyer and the machine
 *          prints available money of the buyer
 *          prints available chocolates and their prices of the machine
 *      asks the user to buy a chocolate, and checks if the chocolate exists or not
 *          if the chocolate does not exist, starts the loop again
 *      asks the user how much chocolate they would like to buy and checks if it is possible to serve them
 *          serving the user is not possible if there is not enough chocolate, or the user does not have enough money to buy
 *              if the user does not have enough money to buy any other chocolate, breaks the loop, and the program exits
 *      if everything is ok, serves the chocolate to the user
 *      at last sets the machine's working state, and starts the loop again
 */
public class Main {

    private static ChocoMachine machine;
    private static Buyer buyer;

    private static double cheapestPrice;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        cheapestPrice = 3.99;
        machine = createChocoMachine();

        System.out.println("Üdv minálunk! Mi a neved?");
        String buyerName = scanner.nextLine();

        buyer = createBuyer(buyerName);

        run(scanner);
    }

    /**
     * Creates a Buyer object with the name given by the user.
     */
    private static Buyer createBuyer(String name) {
        return new Buyer(name, randomInt(20, 71));
    }

    /**
     * Creates a ChocoMachine object filled with chocolates.
     */
    private static ChocoMachine createChocoMachine() {
        Chocolate[] chocolates = getChocolates();

        return new ChocoMachine(chocolates);
    }

    /**
     * Creates Chocolate objects for the ChocoMachine.
     * The prices of the chocolates are calculated from the cheapestPrice field of this class.
     */
    private static Chocolate[] getChocolates() {
        Chocolate choco1 = new Chocolate("Mars", cheapestPrice + 2, 10);
        Chocolate choco2 = new Chocolate("Bounty", cheapestPrice + 1, 10);
        Chocolate choco3 = new Chocolate("MilkyWay", cheapestPrice, 10);

        return new Chocolate[]{choco1, choco2, choco3};
    }

    /**
     * Returns a random whole number (int) between the range of min (inclusive) and max (exclusive).
     */
    private static int randomInt(int min, int max) {
        return (int)(Math.random() * max + min);
    }

    /**
     * Tells that tha ChocoMachine is not working or working.
     * That method works randomly. So there is a slight chance that the machine will break down during operation.
     */
    private static boolean isNotWorking() {
        return randomInt(1, 10) > 9;
    }

    /**
     * Runs the loop to sell chocolates!
     */
    private static void run(Scanner scanner) {
        while (true) {
            if(!isMachineWorking()) {
                break;
            }

            randomRefillChocolate();

            printInfos();

            System.out.println(buyer.getName() + ", mit szeretnél vásárolni? Add meg a sorszámát!");
            int chocoToBuy = scanner.nextInt() - 1;

            if (machine.doesChocolateExist(chocoToBuy)) {
                if (!canMachineServe(scanner, chocoToBuy)) {
                    if (buyer.getMoney() < cheapestPrice) {
                        System.out.println("Ráadásul teljesen kifogyott a tárcád... :( Viszlát!");
                        break;
                    }
                }
            } else {
                System.out.println("Nincs ilyen csoki! Próbáld újra!");
            }

            machine.setOutOfService(isNotWorking());

        }
    }

    /**
     * Prints:
     *      how much money the buyer has
     *      the available chocolates and their prices
     */
    private static void printInfos() {
        System.out.println("\n*** " + buyer.getInfo() + " ***");
        System.out.println("----------------------");
        System.out.println(machine.getInfo());
        System.out.println("----------------------");
    }

    /**
     * Checks that the machine is still working or not. If not, it tells the user.
     * (Failure of the machine will stop the program running.)
     */
    private static boolean isMachineWorking() {
        if (machine.isOutOfService()) {
            System.out.println("Sajnáljuk, a masina elromlott! Próbálkozz újra később.");
            return false;
        }

        return true;
    }

    /**
     * Randomly refills some chocolate in the machine.
     * So there is a chance that the chocolates will never run out.
     */
    private static void randomRefillChocolate() {
        int increaseBy = randomInt(0, 4);
        int chocoToIncrease = randomInt(0, machine.getChocolates().length);

        machine.refillChocolate(chocoToIncrease, increaseBy);
    }

    /**
     * Checks if the machine can serve the wanted chocolate in the wanted amount, and if the user has enough money to buy them.
     * If the user wants more pieces of chocolate than the machine has, or the user does not have enough money, this method returns false.
     * In other cases, the machine serves the chocolate in the wanted amount.
     *
     * If the user does not have enough money, the program will check if the user has any more money than the cheapest chocolate.
     * If the user is totally out of money, the program will exit.
     */
    private static boolean canMachineServe(Scanner scanner, int chocoToBuy) {
        System.out.println("Hány darabot szeretnél venni?");
        int amountToBuy = scanner.nextInt();

        if (machine.canServeChocolate(chocoToBuy, amountToBuy)) {
            Chocolate serveChoco = machine.getChocolate(chocoToBuy);

            if (canBuyerBuy(serveChoco, chocoToBuy, amountToBuy)) {
                System.out.println("Fogyaszd egészséggel a " + serveChoco.getName() + " csokidat!");
            } else {
                System.out.println("Nincs ennyi pénzed! Hupsz!");

                return false;
            }
        } else {
            System.out.println("Nincs ilyen sok csokink! Bocsi!");
            return false;
        }

        return true;
    }

    /**
     * Checks if the user has enough money to buy the wanted chocolate in the wanted amount.
     */
    private static boolean canBuyerBuy(Chocolate wantedChoco, int chocoToBuy, int amountToBuy) {
        double buyerMoney = buyer.getMoney();
        double chocoPrice = wantedChoco.getPrice() * amountToBuy;

        if (buyerMoney >= chocoPrice) {
            machine.serveChocolate(chocoToBuy, amountToBuy);
            buyer.decreaseMoney(chocoPrice);

            return true;
        }

        return false;
    }

}
