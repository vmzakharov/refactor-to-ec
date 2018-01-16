package refactortoec.zoo;

import org.eclipse.collections.api.list.MutableList;

public class Animal
{
    private String name;
    private AnimalType animalType;
    private MutableList<Food> favoriteFoods;

    public Animal(String name, AnimalType animalType, MutableList<Food> favoriteFoods)
    {
        this.name = name;
        this.animalType = animalType;
        this.favoriteFoods = favoriteFoods;
    }

    public MutableList<Food> getFavoriteFoods()
    {
        return this.favoriteFoods;
    }

    public AnimalType getAnimalType()
    {
        return this.animalType;
    }

    public String getName()
    {
        return this.name;
    }

    public int getNumberOfFavoriteFoods()
    {
        return this.favoriteFoods.size();
    }

    @Override
    public String toString()
    {
        return "[" + this.name + ", " + this.animalType + "]";
    }
}
