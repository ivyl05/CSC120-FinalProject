import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

class Crop {

    private Hashtable<CropType, Integer> seeds; 
    private Hashtable<CropType, Integer> cropNumber; 
    private PlayerWallet wallet;

    // Constructor
    public Crop(PlayerWallet wallet) {
        this.seeds = new Hashtable<>();
        this.cropNumber = new Hashtable<>();
        this.wallet = wallet;

        for (CropType crop : CropType.values()) {
            this.seeds.put(crop, 0);
            this.cropNumber.put(crop, 0);
        }
    }


    public void growCrop(CropType crop, int number) {
        if (number <= seeds.get(crop)) {
            if (number > 0) {
                Timer myTimer = new Timer(); 
                TimerTask task = new TimerTask() {
                    public void run() {
                        cropNumber.put(crop, cropNumber.get(crop) + number);
                        System.out.println("You have grown " + number + " " + crop + ".");
                        System.out.println("Now your total number of " + crop + " is " + cropNumber.get(crop) + ".");
                    }
                };

                // Schedule the task based on crop type
                if (crop.equals(CropType.wheat)) {
                    System.out.println("Wheat growing! It will take 10 seconds.");
                    seeds.put(crop, seeds.get(crop) - number);
                    myTimer.schedule(task, 10000);
                } else if (crop.equals(CropType.corn)) {
                    System.out.println("Corn growing! It will take 30 seconds.");
                    seeds.put(crop, seeds.get(crop) - number);
                    myTimer.schedule(task, 30000);
                } else if (crop.equals(CropType.apple)) {
                    System.out.println("Apple growing! It will take 60 seconds.");
                    seeds.put(crop, seeds.get(crop) - number);
                    myTimer.schedule(task, 60000);
                }
            } else {
                throw new RuntimeException("Please enter a valid number.");
            }
        } else {
            throw new RuntimeException("You don't have enough " + crop + " seeds. Purchase some seeds first!");
        }
    }


    public void buySeeds(CropType crop, int number) { // each seed cost 1 coin
        int totalCost = number; 

        if (wallet.spendMoney(totalCost)) {
            if(number>0){
            seeds.put(crop, seeds.get(crop) + number);
            System.out.println("You bought " + number + " " + crop + " seeds for " + totalCost + " coins.");
        }  else {
            throw new RuntimeException("Please enter a valid number.");
        } } else {
            throw new RuntimeException("Not enough money to buy seeds. You need " + totalCost + " coins.");
        }
    }


    public void sellCrops(CropType crop, int number) {
        if (cropNumber.get(crop) >= number) {
            if (number > 0) {
            int earnings = getCropSellPrice(crop) * number;
            cropNumber.put(crop, cropNumber.get(crop) - number);
            wallet.addMoney(earnings);
            System.out.println("You sold " + number + " " + crop + " for " + earnings + " coins.");
        }  else {
            throw new RuntimeException("Please enter a valid number.");
        } 
        }   else {
            throw new RuntimeException("You don't have enough " + crop + " to sell.");
        }
    
    }

    public boolean useCrop(CropType crop, int amount) {
        if (cropNumber.get(crop) >= amount) {
            if (amount > 0) {
            cropNumber.put(crop, cropNumber.get(crop) - amount);
            return true;
        }  else {
            throw new RuntimeException("Please enter a valid number.");
        } }else {
            return false;
        }
    }

    
public void unbuySeeds(CropType crop, int number) {
    int totalCost = number; 
    if (seeds.get(crop) >= number) {
        seeds.put(crop, seeds.get(crop) - number);
        wallet.addMoney(totalCost); 
        System.out.println("Undo buying " + number + " " + crop + " seeds. " + totalCost + " coins are refunded");
    } else {
        throw new RuntimeException("Cannot undo seed purchase. You don't have enough seeds in the inventory.");
    }
}


public void unsellCrops(CropType crop, int number) {
    int earnings = getCropSellPrice(crop) * number;
    if (wallet.spendMoney(earnings)) { 
        cropNumber.put(crop, cropNumber.get(crop) + number);
        System.out.println("Undo selling " + number + " " + crop + ". " + earnings + " coins are refunded.");
    } else {
        System.out.println("Cannot undo crop sale. Not enough money to refund.");
    }
}

    // Get the selling price of a crop
    private int getCropSellPrice(CropType crop) {
        switch (crop) {
            case wheat:
                return 3;
            case corn:
                return 5;
            case apple:
                return 8;
            default:
                return 0;
        }
    }

    public void printSeeds() {
        System.out.println("Here is the current stock of your seeds:");
        for (Map.Entry<CropType, Integer> entry : this.seeds.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void printCropNumber() {
        System.out.println("Here is the current stock of your crops:");
        for (Map.Entry<CropType, Integer> entry : this.cropNumber.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
