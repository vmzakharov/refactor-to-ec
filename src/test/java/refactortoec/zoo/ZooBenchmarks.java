package refactortoec.zoo;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.primitive.IntIntPair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class ZooBenchmarks
{
    private static Food banana = new Food("Banana", FoodType.FRUIT, 50);
    private static Food apple = new Food("Apple", FoodType.FRUIT, 30);
    private static Food cake = new Food("Cake", FoodType.DESSERT, 22);
    private static Food cereal = new Food("Cereal", FoodType.DESSERT, 80);
    private static Food spinach = new Food("Spinach", FoodType.VEGETABLE, 26);
    private static Food carrot = new Food("Carrot", FoodType.VEGETABLE, 27);
    private static Food hamburger = new Food("Hamburger", FoodType.MEAT, 3);

    private static MutableList<Animal> zooAnimals = Lists.mutable.with(
            new Animal("ZigZag", AnimalType.ZEBRA, Lists.mutable.with(banana, apple)),
            new Animal("Tony", AnimalType.TIGER, Lists.mutable.with(cereal, hamburger)),
            new Animal("Phil", AnimalType.GIRAFFE, Lists.mutable.with(cake)),
            new Animal("Lil", AnimalType.GIRAFFE, Lists.mutable.with(spinach)),
            new Animal("Simba", AnimalType.LION, Lists.mutable.with(hamburger)));

    @Benchmark
    public List<Map.Entry<Integer, Long>> highestQuantityFoodJdk()
    {
        return zooAnimals.stream()
                .flatMap(animals -> animals.getFavoriteFoods().stream())
                .collect(Collectors.groupingBy(Food::getQuantity, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(1)
                .collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<IntIntPair> highestQuantityFoodEc()
    {
        return zooAnimals.asLazy()
                .flatCollect(Animal::getFavoriteFoods)
                .collectInt(Food::getQuantity)
                .toBag()
                .topOccurrences(1);
    }

    @Benchmark
    public Map<Integer, String> printAnimalsToNumberOfFavoriteFoodsJdk()
    {
        return zooAnimals.stream()
                .collect(Collectors.groupingBy(
                        Animal::getNumberOfFavoriteFoods,
                        Collectors.mapping(
                                Object::toString,
                                Collectors.joining(","))));
    }

    @Benchmark
    public Map<Integer, String> printAnimalsToNumberOfFavoriteFoodsEc()
    {
        return zooAnimals
                .stream()
                .collect(Collectors.groupingBy(
                        Animal::getNumberOfFavoriteFoods,
                        Collectors2.makeString()));
    }

    @Benchmark
    public Set<Food> uniqueFoodsJdk()
    {
        return zooAnimals.stream()
                .flatMap(each -> each.getFavoriteFoods().stream())
                .collect(Collectors.toSet());
    }

    @Benchmark
    public Set<Food> uniqueFoodsEc()
    {
        MutableSet<Food> set = zooAnimals.flatCollect(Animal::getFavoriteFoods, Sets.mutable.empty());
        MutableSet<Food> set2 = zooAnimals.flatCollect(Animal::getFavoriteFoods).toSet();
        return set;
    }

    @Benchmark
    public Map<FoodType, Integer> typesOfFoodJdk()
    {
        List<FoodType> foodTypes = zooAnimals.stream()
                .flatMap(each -> each.getFavoriteFoods().stream())
                .map(Food::getFoodType)
                .collect(Collectors.toList());

        Map<FoodType, Integer> foodTypeCounts = new HashMap<>();
        for (FoodType foodType : foodTypes)
        {
            Integer count = foodTypeCounts.get(foodType);
            if (count == null)
            {
                count = 0;
            }
            foodTypeCounts.put(foodType, count + 1);
        }
        return foodTypeCounts;
    }

    @Benchmark
    public MutableBag<FoodType> typesOfFoodEc()
    {
        MutableBag<FoodType> foodTypes = zooAnimals
                .flatCollect(Animal::getFavoriteFoods)
                .collect(Food::getFoodType)
                .toBag();
        return foodTypes;
    }
}
