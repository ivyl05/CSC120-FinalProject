/*
 * File name: PlayerWallet.java
 * Description: This class is responsible for managing player's money.
 * Author: Ivy Li
 * Date: 18 December 2024
 */
class PlayerWallet {
    private int money; // stores the current balance of money 

    /**
     * Constructor that initializes a new wallet
     * 
     * @param initialMoney The initial amount of money 
     */
    public PlayerWallet(int initialMoney) {
        this.money = initialMoney;
    }

    /**
     * Gets the current amount of money in the wallet
     *
     * @return The current amount of money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * Adds money to the wallet. It updates the balance and prints the new total.
     *
     * @param amount The amount of money to add to the wallet.
     * @throws RuntimeException if the amount to be added is negative or zero
     */
    public void addMoney(int amount) {
        if (amount > 0) {
            money += amount;
            System.out.println("You earned " + amount + " coins! Your total is now " + money + " coins.");
        } else {
            throw new RuntimeException("Invalid amount to add."); //add to player
        }
    }

    /**
     *  Spend a specified amount of money from the wallet. If sufficient funds are available, it deducts the amount and prints the updated the balance.
     *
     * @param amount The amount of money to spend
     * @return true if the transaction was successful (enough funds were available), false otherwise
     */
    public boolean spendMoney(int amount) {
        if (amount > money) {
            return false;
        } else {
            money -= amount;
            System.out.println("You spent " + amount + " coins. Your remaining balance is " + money + " coins.");
            return true;
        }
    }
}