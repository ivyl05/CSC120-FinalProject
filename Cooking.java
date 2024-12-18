/*
 * File name: Cooking.java
 * Description: Responsible for making the food for the Farm Game
 * Author: Ivy Li
 * Date: 18 December 2024
 */
import java.util.Hashtable;

/**
 * This class is able to make of various food items such as sandwiches, apple pies, pizzas, cheese, and cream.
 * It provides methods for making each type of food, viewing food ingredients, and storing the food produced.
 */
class Cooking {
    private Crop cropInventory; // Inventory of crops 
    private Animal animalInventory; // Inventory of animal products 
    private Hashtable < String, Integer > producedItems; // Record of food items produced

    /**
     * Constructor initializes the the attributes and preloads the initial stock
     * 
     * @param cropInventory the inventory for crops 
     * @param animalInventory the inventory for animal products 
     */
    public Cooking(Crop cropInventory, Animal animalInventory) {
        this.cropInventory = cropInventory;
        this.animalInventory = animalInventory;
        this.producedItems = new Hashtable < > ();
        this.producedItems.put("sandwich", 0);
        this.producedItems.put("applepie", 0);
        this.producedItems.put("pizza", 0);
        this.producedItems.put("cheese", 0);
        this.producedItems.put("cream", 0);
    }
    
    /**
     * Displays the menu of foods that can be made, and their required ingredients
     */
    public void viewMenu() {
        System.out.println("Food Ingredients:");
        System.out.println("1. Sandwich: 2 wheat, 1 egg");
        System.out.println("2. ApplePie: 3 apples, 1 wheat, 2 eggs");
        System.out.println("3. Pizza: 3 wheat, 2 corn, 1 cheese, 1 milk");
        System.out.println("4. Cheese: 1 milk");
        System.out.println("5. Cream: 1 milk");
    }

    /**
     * Makes a sandwich if all ingredients are available
     * 
     * @throws RuntimeException if there are not enough ingredients to make a sandwich
     */
    public void makeSandwich() {
        if (this.cropInventory.useCrop(CropType.wheat, 2) && this.animalInventory.useProduct(AnimalType.chicken, 1)) {
            System.out.println("Making a sandwich! It will be ready in 5 seconds...");
            delay(5000);
            System.out.println("Your sandwich is ready! ⧽(•‿•)⧼");
            this.producedItems.put("sandwich", this.producedItems.get("sandwich") + 1);
        } else {
            throw new RuntimeException("Not enough ingredients to make a sandwich. You need 2 wheat and 1 egg.");
        }
    }

    /**
     * Makes an ApplePie if all ingredients are available
     * 
     * @throws RuntimeException if there are not enough ingredients to make an ApplePie
     */
    public void makeApplePie() {
        if (this.cropInventory.useCrop(CropType.apple, 3) && this.cropInventory.useCrop(CropType.wheat, 1) &&
            this.animalInventory.useProduct(AnimalType.chicken, 2)) {
            System.out.println("Making an apple pie! It will be ready in 10 seconds...");
            delay(10000);
            System.out.println("Your applepie is ready! ⧽(•‿•)⧼");
            producedItems.put("apple pie", this.producedItems.get("apple pie") + 1);
        } else {
            throw new RuntimeException("Not enough ingredients to make an apple pie. You need 3 apples, 1 wheat, and 2 eggs.");
        }
    }

    /**
     * Makes a pizza if all ingredients are available
     * 
     * @throws RuntimeException if there are not enough ingredients to make a pizza
     */
    public void makePizza() {
        if (this.cropInventory.useCrop(CropType.wheat, 3) && this.cropInventory.useCrop(CropType.corn, 2) &&
            this.producedItems.get("cheese") > 0 && this.animalInventory.useProduct(AnimalType.cow, 1)) {
            this.producedItems.put("cheese", this.producedItems.get("cheese") - 1);
            System.out.println("Making a pizza! It will be ready in 15 seconds...");
            delay(15000);
            System.out.println("Your pizza is ready! !⧽(•‿•)⧼");
            this.producedItems.put("pizza", this.producedItems.get("pizza") + 1);
        } else {
            throw new RuntimeException("Not enough ingredients to make a pizza. You need 3 wheat, 2 corn, 1 cheese, and 1 milk.");
        }
    }

    /**
     * Makes a cheese if all ingredients are available
     * 
     * @throws RuntimeException if there are not enough ingredients to make a cheese
     */
    public void makeCheese() {
        if (this.animalInventory.useProduct(AnimalType.cow, 1)) {
            System.out.println("Making cheese! It will be ready in 5 seconds...");
            delay(5000);
            this.producedItems.put("cheese", this.producedItems.get("cheese") + 1);
            System.out.println("You made 1 cheese! Now you have " + this.producedItems.get("cheese") + " cheese. ⧽(•‿•)⧼");
        } else {
            throw new RuntimeException("Not enough milk to make cheese. You need 1 milk.");
        }
    }

    /**
     * Makes a crea  if all ingredients are available
     * 
     * @throws RuntimeException if there are not enough ingredients to make a cream
     */
    public void makeCream() {
        if (this.animalInventory.useProduct(AnimalType.cow, 1)) {
            System.out.println("Making cream! It will be ready in 5 seconds...");
            delay(5000);
            this.producedItems.put("cream", this.producedItems.get("cream") + 1);
            System.out.println("You made 1 cream! Now you have " + this.producedItems.get("cream") + " cream. ⧽(•‿•)⧼");
        } else {
            throw new RuntimeException("Not enough milk to make cream. You need 1 milk.");
        }
    }

    /**
     * Delays the thread for a specified number of milliseconds depending on which food is being cooked
     * 
     * @param milliseconds the time in milliseconds to delay
     */
    public void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Opps! An error occurred while Cooking.");
        }
    }

    /**
     * Print all produced food items
     */
    public void viewProducedItems() {
        for (String item: this.producedItems.keySet()) {
            System.out.println(item + ": " + this.producedItems.get(item));
        }
    }
}