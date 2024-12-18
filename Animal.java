/*
 * File name: Animal.java
 * Description: Responsible for the animals in the Farm Game 
 * Author: Ivy Li
 * Date: 18 December 2024
 */
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

class Animal {
    private Hashtable < AnimalType, Integer > animals; // Maps animal types to the numbers of animals in player's inventory
    private Hashtable < AnimalType, Integer > products; // Maps animal types to the number of products they produced
    private Hashtable < AnimalType, Timer > animalTimers; // Maps animal types to timers that manage production
    private PlayerWallet wallet; // make a new PlayerWallet

    /**
     * Constructor initializes the attributes and sets up timers for each animal type.
     * 
     * @param wallet the wallet associated with the player, used for financial transactions.
     */
    public Animal(PlayerWallet wallet) {
        this.wallet = wallet;
        this.animals = new Hashtable < > ();
        this.products = new Hashtable < > ();
        this.animalTimers = new Hashtable < > ();

        for (AnimalType animal: AnimalType.values()) {
            this.animals.put(animal, 0);
            this.products.put(animal, 0);
            this.animalTimers.put(animal, new Timer(true));
        }
    }

    /**
     * Purchases a specified number of animals of a given type and starts their production timer.
     * 
     * @param animal the type of animal to buy.
     * @param number the number of animals to buy.
     * @throws RuntimeException if the number of animals to buy is 0 or negative, or if there is not enough money.
     */
    public void buyAnimal(AnimalType animal, int number) {
        if (number <= 0) {
            throw new RuntimeException("Invalid number of animals.");
        }

        int costPerAnimal = getAnimalCost(animal);
        int totalCost = costPerAnimal * number;

        if (this.wallet.spendMoney(totalCost)) {
            animals.put(animal, this.animals.get(animal) + number);
            System.out.println("You bought " + number + " " + animal + "(s) for " + totalCost + " coins!");
            startProductionTimer(animal); // Start product generation timer
        } else {
            throw new RuntimeException("Not enough coins to buy " + number + " " + animal + "(s). You need " + totalCost + " coins.");
        }
    }

    /**
     * Starts a timer to produce products from the specified animal type at regular intervals
     * 
     * @param animal the type of animal to start producing products from
     */
    private void startProductionTimer(AnimalType animal) {
        int interval = getProductionInterval(animal);
        Timer timer = animalTimers.get(animal);

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                int count = animals.get(animal);
                if (count > 0) {
                    products.put(animal, products.get(animal) + count);
                    System.out.println(count + " " + animal.getProduct() + "(s) were produced by your " + animal + "(s).");
                }
            }
        }, interval, interval);
    }

    /**
     * Starts a timer to produce products from the specified animal type at regular intervals
     * 
     * @param animal the type of animal to start producing products from
     */
    private int getProductionInterval(AnimalType animal) {
        switch (animal) {
        case chicken:
            return 15000; //15 sec
        case cow:
            return 20000; //20 sec
        case pig:
            return 30000; //30 sec
        default:
            return 10000;
        }
    }

    /**
     * Get the cost of a type of animal
     * 
     * @param animal the type of animal
     * @return the cost per animal
     */
    private int getAnimalCost(AnimalType animal) {
        switch (animal) {
        case chicken:
            return 3;
        case cow:
            return 5;
        case pig:
            return 8;
        default:
            return 0;
        }
    }

    /**
     * Prints the current inventory of animals owned by the player.
     */
    public void viewAnimals() {
        System.out.println("Your animals:");
        for (AnimalType animal: AnimalType.values()) {
            System.out.println(animal + ": " + animals.get(animal));
        }
    }
    
    /**
     * Prints the current inventory of products collected from animals.
     */
    public void viewProducts() {
        System.out.println("Your collected products:");
        for (AnimalType animal: AnimalType.values()) {
            System.out.println(animal.getProduct() + ": " + products.get(animal));
        }
    }

    /**
     * Uses a specified amount of products of a given type, if available.
     * 
     * @param animal the type of product to use.
     * @param amount the amount of the product to use.
     * @return true if the product was used successfully, false if insufficient product was available.
     */
    public boolean useProduct(AnimalType animal, int amount) {
        if (this.products.get(animal) >= amount) {
            this.products.put(animal, this.products.get(animal) - amount);
            return true;
        } else {
            System.out.println("Not enough " + animal.getProduct() + " available.");
            return false;
        }
    }

    /**
     * Reverses the purchase of animals, refunding the cost and adjusting inventory accordingly.
     * 
     * @param animal the type of animal to unbuy.
     * @param number the number of animals to unbuy.
     * @throws RuntimeException if the transaction cannot be undone due to insufficient animals in inventory.
     */
    public void unbuyAnimal(AnimalType animal, int number) {
        int costPerAnimal = getAnimalCost(animal);
        int totalCost = costPerAnimal * number;
        if (this.animals.get(animal) >= number) {
            this.animals.put(animal, this.animals.get(animal) - number);
            this.wallet.addMoney(totalCost);
            System.out.println("Undo buying" + number + " " + animal + "(s).  " + totalCost + " coins is refunded to your wallet.");
        } else {
            System.out.println("Cannot undo animal purchase. Not enough animals in inventory.");
        }
    }

}