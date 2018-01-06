package refactortoec.zoo;

public class Food
{
    private String name;
    private FoodType foodType;
    private int quantity;

    public Food(String name, FoodType foodType, int quantity)
    {
        this.name = name;
        this.foodType = foodType;
        this.quantity = quantity;
    }

    public String getName()
    {
        return this.name;
    }

    public FoodType getFoodType()
    {
        return this.foodType;
    }

    public int getQuantity()
    {
        return this.quantity;
    }
}
