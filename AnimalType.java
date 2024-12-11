enum AnimalType {
    chicken("eggs"),
    cow("milk"),
    pig("bacon");

    private final String product;

    AnimalType(String product) {
        this.product = product;
    }
    public String getProduct() {
        return product;
    }
}
