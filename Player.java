
import java.util.*;

class Player {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        PlayerWallet wallet = new PlayerWallet(50);
        Crop myPlant = new Crop(wallet);
        Animal myAnimals = new Animal(wallet);
        Cooking myCooking = new Cooking(myPlant, myAnimals);
        boolean running = true;

        // String seedType, cropType, animalType;
        // int seedCount, cropCount, animalCount;

        // Randomized 
        int sandwichGoal = random.nextInt(2)+1 ; 
        int applePieGoal = random.nextInt(2) ;
        int pizzaGoal = random.nextInt(2) ; 
        int cheeseGoal = random.nextInt(2) ; 
        int creamGoal = random.nextInt(2) ; 

        int sandwichesMade = 0;
        int applePiesMade = 0;
        int pizzasMade = 0;
        int cheesesMade = 0;
        int creamsMade = 0;

        int timeLimit = 600; // 10 minutes
        long startTime = System.currentTimeMillis(); // current time 

        System.out.println("Welcome to Ivy's farm! (˶◕‿◕˶✿) What's your name?");
        String name = scanner.nextLine();
        System.out.println("Hello " + name + "! Unfortunately, Ivy's out of town today. Can you help her run the farm?");
        String choice = scanner.nextLine();

        while (true) {
            choice = choice.toLowerCase();
            if (choice.equals("yes")) {
                System.out.println("Great, let's get started! We have a busy day ahead!");
                System.out.println("Your goals for today are:");
                System.out.println("1. Make " + sandwichGoal + " sandwiches.");
                System.out.println("2. Make " + applePieGoal + " apple pies.");
                System.out.println("3. Make " + pizzaGoal + " pizzas.");
                System.out.println("4. Make " + cheeseGoal + " cheese.");
                System.out.println("5. Make " + creamGoal + " cream.");
                System.out.println("Complete these goals within 10 minutes! If your wallet balance falls below 0, you lose. Good luck! \n" + //
                                        "(っ´▽｀)っ");
                break;
            } else if (choice.equals("no")) {
                System.out.println("That's okay. Maybe next time!");
                System.exit(0);
            } else {
                System.out.println("Please answer with 'yes' or 'no'.");
                choice = scanner.nextLine();
            }
        }

        while (running) {
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // convert from miliseconds to seconds
            if (elapsedTime >= timeLimit) {
                System.out.println("Time's up! You couldn't complete the goals in time. You lose (╥﹏╥). Good luck next Time!");
                break;
            }

            if (wallet.getMoney() < 0) {
                System.out.println("Your wallet balance fell below 0. You went broke! Game over! \n" + //
                                        "(⋟﹏⋞)");
                break;
            }

            System.out.println("\nTime remaining: " + (timeLimit - elapsedTime) + " seconds."); //time remaining
            System.out.println("Choose an action:");
            System.out.println("1. Buy seeds");
            System.out.println("2. Plant crops");
            System.out.println("3. Sell crops");
            System.out.println("4. Buy animals");
            // System.out.println("5. Collect animal products");
            System.out.println("5. Cook food");
            System.out.println("6. View inventory");
            System.out.println("7. View wallet");
            System.out.println("8. View food ingredients");
            System.out.println("9. Undo buy seeds");
            System.out.println("10. Undo sell crops");
            System.out.println("11. Undo buy animals");
            System.out.println("12. Exit");
            System.out.print("Your choice: ");

            try {
                int option = scanner.nextInt();
                System.out.println();

                switch (option) {
                    case 1: 
                        System.out.println("What type of seeds are you looking for? (wheat, corn, apple)");
                        String seedType = scanner.next();
                        System.out.println("How many seeds are you buying?");
                        int seedCount = scanner.nextInt();
                        try {
                            CropType cropType = CropType.valueOf(seedType.toLowerCase());
                            myPlant.buySeeds(cropType, seedCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Oh no! Ivy's farm doesn't have this type of crop. Try again. \n" + //
                                                                "(ᗒᗣᗕ)՞");
                        }
                        catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 2: 
                        System.out.println("What crop are you planting? (wheat, corn, apple)");
                        String cropTypeToPlant = scanner.next();
                        System.out.println("How many?");
                        int cropCount = scanner.nextInt();
                        try {
                            CropType cropType = CropType.valueOf(cropTypeToPlant.toLowerCase());
                            myPlant.growCrop(cropType, cropCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Oh no! Ivy's farm doesn't have this type of crop. Try again. \n" + //
                                                                "(ᗒᗣᗕ)՞");
                        }catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3: 
                        System.out.println("What crop do you want to sell? (wheat, corn, apple)");
                        String cropTypeToSell = scanner.next();
                        System.out.println("How many are you selling?");
                        int sellCount = scanner.nextInt();
                        try {
                            CropType cropType = CropType.valueOf(cropTypeToSell.toLowerCase());
                            myPlant.sellCrops(cropType, sellCount);
                        }catch (IllegalArgumentException e) {
                            System.out.println("Oh no! Ivy's farm doesn't have this type of crop. Try again.\n" + //
                                                                "(ᗒᗣᗕ)՞");
                        }catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4: 
                        System.out.println("Which animal would you like to buy? (chicken, cow, pig)");
                        String animalType = scanner.next();
                        System.out.println("How many are you buying? ");
                        int animalCount = scanner.nextInt();
                        try {
                            AnimalType animal = AnimalType.valueOf(animalType.toLowerCase());
                            myAnimals.buyAnimal(animal, animalCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Opps! Invalid animal type \n" + //
                                                                "(ᗒᗣᗕ)՞");
                        }
                        catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    // case 5: 
                    //     myAnimals.collectProducts();
                    //     break;

                    case 5: 
                        System.out.println("What would you like to cook? (sandwich, apple pie, pizza, cheese, cream)");
                        String foodChoice = scanner.next().toLowerCase();
                        try{ switch (foodChoice) {
                            case "sandwich":
                                //myCooking.makeFood("sandwich");
                                myCooking.makeSandwich();
                                sandwichesMade++;
                                break;

                            case "apple pie": //Some reason cannot make apple pie 
                                //myCooking.makeFood("apple pie");
                                myCooking.makeApplePie();
                                applePiesMade++;
                                break;

                            case "pizza":
                                //myCooking.makeFood("pizza");
                                myCooking.makePizza();
                                pizzasMade++;
                                break;

                            case "cheese":
                                //myCooking.makeFood("cheese");
                                myCooking.makeCheese();
                                cheesesMade++;
                                break;

                            case "cream":
                                //myCooking.makeFood("cream");
                                myCooking.makeCream();
                                creamsMade++;
                                break;

                            default:
                                System.out.println("Invalid food type! \n" + //
                                                                        "(ᗒᗣᗕ)՞");
                        }
                    } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }

                   
                        if (sandwichesMade >= sandwichGoal &&
                            applePiesMade >= applePieGoal &&
                            pizzasMade >= pizzaGoal &&
                            cheesesMade >= cheeseGoal &&
                            creamsMade >= creamGoal) {
                            System.out.println("Congratulations! You achieved your goals and won the game!");
                            running = false;
                        }
                        break;

                    case 6: 
                        System.out.println("Inventory:");
                        System.out.println("\nSeeds:");
                        myPlant.printSeeds();
                        System.out.println("\nCrops:");
                        myPlant.printCropNumber();
                        System.out.println("\nAnimals:");
                        myAnimals.viewAnimals();
                        System.out.println("\nAnimal Products:");
                        myAnimals.viewProducts();
                        System.out.println("\nProduced Items:");
                        myCooking.viewProducedItems();
                        break;

                    case 7: 
                        System.out.println("Your current balance: " + wallet.getMoney() + " coins.");
                        break;

                    case 8: 
                        myCooking.viewMenu();
                        break;

                        case 9: 
                        System.out.println("Enter crop type for undoing seed purchase:");
                        String undoseedType = scanner.next();
                        System.out.println("Enter the number of seeds to undo purchase:");
                        int undoseedCount = scanner.nextInt();
                        try {
                            CropType cropType = CropType.valueOf(undoseedType.toUpperCase());
                            myPlant.unbuySeeds(cropType, undoseedCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid crop type. Please try again.");
                        }
                        break;
                    case 10: 
                        System.out.println("Enter crop type for undoing crop sale:");
                        String cropType = scanner.next();
                        System.out.println("Enter the number of crops to undo sale:");
                        int undocropCount = scanner.nextInt();
                        try {
                            CropType crop = CropType.valueOf(cropType.toUpperCase());
                            myPlant.unsellCrops(crop, undocropCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid crop type. Please try again.");
                        }
                        break;
                    case 11: 
                        System.out.println("Which animal purchase would you like to undo? (chicken, cow, pig)");
                        String undoanimalType = scanner.next();
                        System.out.println("Enter the number of animals to undo purchase:");
                        int undoanimalCount = scanner.nextInt();
                        try {
                            AnimalType animal = AnimalType.valueOf(undoanimalType.toUpperCase());
                            myAnimals.unbuyAnimal(animal, undoanimalCount);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid animal type. Please try again.");
                        }
                        break;
                    case 12: 
                        running = false;
                        System.out.println("｡･ﾟﾟ･(థ Д థ。)･ﾟﾟ･｡ Sorry to see you go. Hope you will visit again!");
                        break;
                    default:
                        System.out.println("Please enter a number between 1 and 10.");
                }
            } catch (RuntimeException e) {
                System.out.println("Cannot recognize! Please try again.");
                scanner.next(); 
            }
        }
        scanner.close();
    }
}
