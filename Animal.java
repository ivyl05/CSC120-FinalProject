import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

class Animal {
    private Hashtable<AnimalType, Integer> animals; 
    private Hashtable<AnimalType, Integer> products; 
    private Hashtable<AnimalType, Timer> animalTimers;
    private PlayerWallet wallet; 

    public Animal(PlayerWallet wallet) {
        this.wallet = wallet;
        animals = new Hashtable<>();
        products = new Hashtable<>();
        animalTimers = new Hashtable<>();
        

        for (AnimalType animal : AnimalType.values()) { //AI generated
            animals.put(animal, 0);
            products.put(animal, 0);
            animalTimers.put(animal, new Timer(true)); 
        }
    }

    public void buyAnimal(AnimalType animal, int number) {
        if (number <= 0) {
            throw new RuntimeException("Invalid number of animals.");
        }

        int costPerAnimal = getAnimalCost(animal);
        int totalCost = costPerAnimal * number;

        if (wallet.spendMoney(totalCost)) {
            animals.put(animal, animals.get(animal) + number);
            System.out.println("You bought " + number + " " + animal + "(s) for " + totalCost + " coins!");
            startProductionTimer(animal); // Start product generation timer
        } else {
            System.out.println("Not enough coins to buy " + number + " " + animal + "(s). You need " + totalCost + " coins.");
        }
    }

    private void startProductionTimer(AnimalType animal) { //ideas from GPT
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

    private int getProductionInterval(AnimalType animal) {
        switch (animal) {
            case chicken:
                return 10000; //10 sec
            case cow:
                return 20000; //20 sec
            case pig:
                return 30000; // 30 sec
            default:
                return 10000; // Default interval
        }
    }

    private int getAnimalCost(AnimalType animal) {
        switch (animal) {
            case chicken:
                return 3; //  cost 3 coins
            case cow:
                return 5; //  cost 5 coins
            case pig:
                return 8; //  cost 8 coins
            default:
                return 0; 
        }
    }

    // public void collectProducts() {
    //     for (AnimalType animal : AnimalType.values()) {
    //         int collected = products.get(animal);
    //         if (collected > 0) {
    //             System.out.println("You collected " + collected + " " + animal.getProduct() + "(s) from your " + animal + "(s)!");
    //             products.put(animal, 0); // Reset collected products after collection
    //         } else {
    //             System.out.println("No " + animal.getProduct() + " available to collect.");
    //         }
    //     }
    // }

    public void viewAnimals() {
        System.out.println("Your animals:");
        for (AnimalType animal : AnimalType.values()) {
            System.out.println(animal + ": " + animals.get(animal));
        }
    }

    public void viewProducts() {
        System.out.println("Your collected products:");
        for (AnimalType animal : AnimalType.values()) {
            System.out.println(animal.getProduct() + ": " + products.get(animal));
        }
    }

    public boolean useProduct(AnimalType animal, int amount) {
        if (products.get(animal) >= amount) {
            products.put(animal, products.get(animal) - amount);
            return true;
        } else {
            System.out.println("Not enough " + animal.getProduct() + " available.");
            return false;
        }
    }

    // Method to unbuy animals
public void unbuyAnimal(AnimalType animal, int number) {
    int costPerAnimal = getAnimalCost(animal);
    int totalCost = costPerAnimal * number;
    if (animals.get(animal) >= number) {
        animals.put(animal, animals.get(animal) - number); // Remove the animals from inventory
        wallet.addMoney(totalCost); 
        System.out.println("Undo buying" + number + " " + animal + "(s).  " + totalCost + " coins is refunded to your wallet.");
    } else {
        System.out.println("Cannot undo animal purchase. Not enough animals in inventory.");
    }
}

}
