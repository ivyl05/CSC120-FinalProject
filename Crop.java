/*
 * File name: Crop.java
 * Description: Responsible for operations related to crops in the Farm Game
 * Author: Ivy Li
 * Date: 18 December 2024
 */

 import java.util.Hashtable;
 import java.util.Map;
 import java.util.Timer;
 import java.util.TimerTask;
 
 /**
  * This class is responsible for operations related to crops, including buying seeds, growing crops, and selling the harvest
  * This class uses timers for growth periods for different types of crops.
  * 
  */
 class Crop {
 
     private Hashtable < CropType, Integer > seeds; // number of seeds of each crop type
     private Hashtable < CropType, Integer > cropNumber; // number of grown crops of each type
     private PlayerWallet wallet; // make a new PlayerWallet to store money
 
     /**
      * Constructs a new Crop instance. Initialize the inventories for seeds and crops, and set up the wallet.
      * 
      * @param wallet The wallet used storing money 
      */
     public Crop(PlayerWallet wallet) {
         this.seeds = new Hashtable < > ();
         this.cropNumber = new Hashtable < > ();
         this.wallet = wallet;
 
         for (CropType crop: CropType.values()) {
             this.seeds.put(crop, 0);
             this.cropNumber.put(crop, 0);
         }
     }
 
     /**
      * Grows a particular number of a crop type if enough seeds are available
      * 
      * @param crop The type of crop to grow
      * @param number The number of crops to grow
      * @throws RuntimeException if there are not enough seeds or the number is invalid
      */
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
 
     /**
      * Buys a specific number of seeds for a specific crop type. Each seed use coins from the wallet. 
      * 
      * @param crop The crop type wish to buy
      * @param number The number of seeds to buy
      * @throws RuntimeException if the player does not have enough money or the number is invalid
      */
     public void buySeeds(CropType crop, int number) { // each seed cost 1 coin
         int totalCost = number;
 
         if (wallet.spendMoney(totalCost)) {
             if (number > 0) {
                 seeds.put(crop, seeds.get(crop) + number);
                 System.out.println("You bought " + number + " " + crop + " seeds for " + totalCost + " coins.");
             } else {
                 throw new RuntimeException("Please enter a valid number.");
             }
         } else {
             throw new RuntimeException("Not enough money to buy seeds. You need " + totalCost + " coins.");
         }
     }
 
     /**
      * Sells a specific number of crops and add coins to the wallet
      * 
      * @param crop The crop type to sell
      * @param number The number of crops to sell
      * @throws RuntimeException if the player does not have enough crops or the number is invalid
      */
     public void sellCrops(CropType crop, int number) {
         if (cropNumber.get(crop) >= number) {
             if (number > 0) {
                 int earnings = getCropSellPrice(crop) * number;
                 cropNumber.put(crop, cropNumber.get(crop) - number);
                 wallet.addMoney(earnings);
                 System.out.println("You sold " + number + " " + crop + " for " + earnings + " coins.");
             } else {
                 throw new RuntimeException("Please enter a valid number.");
             }
         } else {
             throw new RuntimeException("You don't have enough " + crop + " to sell.");
         }
     }
 
     /**
      * Uses a specific amount of crops for cooking or feeding animals
      * 
      * @param crop The crop type to use
      * @param amount The amount of the crop to use
      * @return true if the crops were used successfully, false if there are not enough crops
      * @throws RuntimeException if the amount is invalid
      */
     public boolean useCrop(CropType crop, int amount) {
         if (cropNumber.get(crop) >= amount) {
             if (amount > 0) {
                 cropNumber.put(crop, cropNumber.get(crop) - amount);
                 return true;
             } else {
                 throw new RuntimeException("Please enter a valid number.");
             }
         } else {
             return false;
         }
     }
 
     /**
      * Reverses a seed purchase aned refunds the cost and adjust the seed inventory.
      * 
      * @param crop The crop type to reverse the seed purchase
      * @param number The number of seeds to reverse the purchase for
      * @throws RuntimeException if the transaction cannot be undone due to insufficient seeds in inventory
      */
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
 
     /**
      * Reverses a crop sale, and deducts the refunded amount from the player's wallet and adjusts the crop inventory
      * 
      * @param crop The crop type to reverse
      * @param number The number of crops to reverse the sale for
      * @throws RuntimeException if there is not enough money in the wallet to complete the refund
      */
     public void unsellCrops(CropType crop, int number) {
         int earnings = getCropSellPrice(crop) * number;
         if (wallet.spendMoney(earnings)) {
             cropNumber.put(crop, cropNumber.get(crop) + number);
             System.out.println("Undo selling " + number + " " + crop + ". " + earnings + " coins are refunded.");
         } else {
             System.out.println("Cannot undo crop sale. Not enough money to refund.");
         }
     }
 
     /**
      * Gets the selling price for a specific crop type
      * 
      * @param crop The crop type
      * @return The selling price of the crop
      */
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
 
     /**
      * Prints the current inventory of seeds
      */
     public void printSeeds() {
         System.out.println("Here is the current stock of your seeds:");
         for (Map.Entry < CropType, Integer > entry: this.seeds.entrySet()) {
             System.out.println(entry.getKey() + ": " + entry.getValue());
         }
     }
 
     /**
      * Prints the current inventory of grown crops
      */
     public void printCropNumber() {
         System.out.println("Here is the current stock of your crops:");
         for (Map.Entry < CropType, Integer > entry: this.cropNumber.entrySet()) {
             System.out.println(entry.getKey() + ": " + entry.getValue());
         }
     }
 }