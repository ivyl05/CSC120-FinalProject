/*
 * File name: AnimalType.java
 * Description: Creates an enum for animal types
 * Author: Ivy Li
 * Date: 18 December 2024
 */

 enum AnimalType {
    chicken("eggs"), // chicken produces eggs
        cow("milk"), // cow produces milk
        pig("bacon"); // pig produces bacon

    private final String product; //the product type associated with the animal

    /**
     * Constructor for the enum constant
     *
     * @param product the product that the animal produces, used to initialize the product field
     */
    AnimalType(String product) {
        this.product = product;
    }
    
    /**
     * Get the product associated with the animal
     *
     * @return the product
     */
    public String getProduct() {
        return product;
    }
}