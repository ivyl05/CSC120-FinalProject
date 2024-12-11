import java.util.Hashtable;

class Cooking {
    private Crop cropInventory; 
    private Animal animalInventory;
    private Hashtable<String, Integer> producedItems; 

    public Cooking(Crop cropInventory, Animal animalInventory) {
        this.cropInventory = cropInventory;
        this.animalInventory = animalInventory;
        this.producedItems = new Hashtable<>();
        producedItems.put("sandwich", 0); 
        producedItems.put("apple pie", 0);
        producedItems.put("pizza", 0); 
        producedItems.put("cheese", 0);
        producedItems.put("cream", 0); 
    }

    public void makeFood(String foodType) {
        switch (foodType.toLowerCase()) {
            case "sandwich":
                makeSandwich();
                break;
            case "apple pie":
                makeApplePie();
                break;
            case "pizza":
                makePizza();
                break;
            case "cheese":
                makeCheese();
                break;
            case "cream":
                makeCream();
                break;
            default:
                System.out.println("Invalid food type. Please choose a food option from above.");
        }
    }

    public void viewMenu() {
        System.out.println("Food Ingredients:");
        System.out.println("1. Sandwich: 2 wheat, 1 egg");
        System.out.println("2. Apple Pie: 3 apples, 1 wheat, 2 eggs");
        System.out.println("3. Pizza: 3 wheat, 2 corn, 1 cheese, 1 milk");
        System.out.println("4. Cheese: 1 milk");
        System.out.println("5. Cream: 1 milk");
    }

    public void makeSandwich() {
        if (cropInventory.useCrop(CropType.wheat, 2) && animalInventory.useProduct(AnimalType.chicken, 1)) {
            System.out.println("Making a sandwich! It will be ready in 5 seconds...");
            delay(5000);
            System.out.println("Your sandwich is ready! ⧽(•‿•)⧼");
            producedItems.put("sandwich", producedItems.get("sandwich") + 1);
        } else {
            throw new RuntimeException("Not enough ingredients to make a sandwich. You need 2 wheat and 1 egg.");
        }
    }

    public void makeApplePie() {
        if (cropInventory.useCrop(CropType.apple, 3) && cropInventory.useCrop(CropType.wheat, 1) &&
            animalInventory.useProduct(AnimalType.chicken, 2)) {
            System.out.println("Making an apple pie! It will be ready in 10 seconds...");
            delay(10000);
            System.out.println("Your apple pie is ready! ⧽(•‿•)⧼");
            producedItems.put("apple pie", producedItems.get("apple pie") + 1);
        } else {
            throw new RuntimeException("Not enough ingredients to make an apple pie. You need 3 apples, 1 wheat, and 2 eggs.");
        }
    }

    public void makePizza() {
        if (cropInventory.useCrop(CropType.wheat, 3) && cropInventory.useCrop(CropType.corn, 2) &&
            producedItems.get("cheese") > 0 && animalInventory.useProduct(AnimalType.cow, 1)) {
            producedItems.put("cheese", producedItems.get("cheese") - 1);
            System.out.println("Making a pizza! It will be ready in 15 seconds...");
            delay(15000);
            System.out.println("Your pizza is ready! !⧽(•‿•)⧼");
            producedItems.put("pizza", producedItems.get("pizza") + 1);
        } else {
            throw new RuntimeException("Not enough ingredients to make a pizza. You need 3 wheat, 2 corn, 1 cheese, and 1 milk.");
        }
    }

    public void makeCheese() {
        if (animalInventory.useProduct(AnimalType.cow, 1)) {
            System.out.println("Making cheese! It will be ready in 5 seconds...");
            delay(5000);
            producedItems.put("cheese", producedItems.get("cheese") + 1);
            System.out.println("You made 1 cheese! Now you have " + producedItems.get("cheese") + " cheese. ⧽(•‿•)⧼");
        } else {
            throw new RuntimeException("Not enough milk to make cheese. You need 1 milk.");
        }
    }

    public void makeCream() {
        if (animalInventory.useProduct(AnimalType.cow, 1)) {
            System.out.println("Making cream! It will be ready in 5 seconds...");
            delay(5000);
            producedItems.put("cream", producedItems.get("cream") + 1);
            System.out.println("You made 1 cream! Now you have " + producedItems.get("cream") + " cream. ⧽(•‿•)⧼");
        } else {
            throw new RuntimeException("Not enough milk to make cream. You need 1 milk.");
        }
    }

    public void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Opps! An error occurred while Cooking.");
        }
    }

    public void viewProducedItems() {
        for (String item : producedItems.keySet()) {
            System.out.println(item + ": " + producedItems.get(item));
        }
    }
}

