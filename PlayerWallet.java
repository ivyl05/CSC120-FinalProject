class PlayerWallet { 
    private int money;

    public PlayerWallet(int initialMoney) {
        this.money = initialMoney;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        if (amount > 0) {
            money += amount;
            System.out.println("You earned " + amount + " coins! Your total is now " + money + " coins.");
        } else {
            throw new RuntimeException("Invalid amount to add."); //add to player
        }
    }

    public boolean spendMoney(int amount) {
        if (amount > money) {
            return false; // Not enough money
        } else {
            money -= amount;
            System.out.println("You spent " + amount + " coins. Your remaining balance is " + money + " coins.");
            return true;
        }
    }
}

