package refactortoec.benchmarks;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.openjdk.jmh.annotations.*;
import refactortoec.zoo.Animal;
import refactortoec.zoo.AnimalType;
import refactortoec.zoo.Food;
import refactortoec.zoo.FoodType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class ZooBenchmarks
{
    private static final Food BANANA = new Food("Banana", FoodType.FRUIT, 50);
    private static final Food APPLE = new Food("Apple", FoodType.FRUIT, 30);
    private static final Food CAKE = new Food("Cake", FoodType.DESSERT, 22);
    private static final Food CEREAL = new Food("Cereal", FoodType.DESSERT, 80);
    private static final Food SPINACH = new Food("Spinach", FoodType.VEGETABLE, 26);
    private static final Food CARROT = new Food("Carrot", FoodType.VEGETABLE, 27);
    private static final Food HAMBURGER = new Food("Hamburger", FoodType.MEAT, 3);

    private static MutableList<Animal> zooAnimals = Lists.mutable.with(
            new Animal("ZigZag", AnimalType.ZEBRA, Lists.mutable.with(BANANA, APPLE)),
            new Animal("Tony", AnimalType.TIGER, Lists.mutable.with(CEREAL, HAMBURGER)),
            new Animal("Phil", AnimalType.GIRAFFE, Lists.mutable.with(CAKE, CARROT)),
            new Animal("Lil", AnimalType.GIRAFFE, Lists.mutable.with(SPINACH)),
            new Animal("Simba", AnimalType.LION, Lists.mutable.with(HAMBURGER)));

    @Benchmark
    public List<Map.Entry<Food, Long>> mostPopularFoodItemJdk()
    {
        //output: [Hamburger=2]
        return zooAnimals.stream()
                .flatMap(animals -> animals.getFavoriteFoods().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Food, Long>comparingByValue().reversed())
                .limit(1)
                .collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<ObjectIntPair<Food>> mostPopularFoodItemEc()
    {
        //output: [Hamburger:2]
        MutableList<ObjectIntPair<Food>> intIntPairs = zooAnimals.asLazy()
                .flatCollect(Animal::getFavoriteFoods)
                .toBag()
                .topOccurrences(1);
        return intIntPairs;
    }

    @Benchmark
    public Map<Integer, String> printNumberOfFavoriteFoodItemsToAnimalsJdk()
    {
        //output: {1=[Lil, GIRAFFE],[Simba, LION], 2=[ZigZag, ZEBRA],[Tony, TIGER],[Phil, GIRAFFE]}
        return zooAnimals.stream()
                .collect(Collectors.groupingBy(
                        Animal::getNumberOfFavoriteFoods,
                        Collectors.mapping(
                                Object::toString,
                                Collectors.joining(","))));
    }

    @Benchmark
    public Map<Integer, String> printNumberOfFavoriteFoodItemsToAnimalsEc()
    {
        //output: {1=[Lil, GIRAFFE], [Simba, LION], 2=[ZigZag, ZEBRA], [Tony, TIGER], [Phil, GIRAFFE]}
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
    public Set<Food> uniqueFoodsEcWithTargetCollection()
    {
        return zooAnimals.flatCollect(Animal::getFavoriteFoods, Sets.mutable.empty());
    }

    @Benchmark
    public Set<Food> uniqueFoodsEcWithoutTargetCollection()
    {
        return zooAnimals.flatCollect(Animal::getFavoriteFoods).toSet();
    }

    @Benchmark
    public Map<Boolean, List<Animal>> getMeatAndNonMeatEatersJdk()
    {
        java.util.function.Predicate<Animal> eatsMeat = animal ->
                animal.getFavoriteFoods().stream().anyMatch(food -> food.getFoodType() == FoodType.MEAT);

        Map<Boolean, List<Animal>> meatAndNonMeatEaters = zooAnimals
                .stream()
                .collect(Collectors.partitioningBy(eatsMeat));
        //output = {false=[[ZigZag, ZEBRA], [Phil, GIRAFFE], [Lil, GIRAFFE]], true=[[Tony, TIGER], [Simba, LION]]}
        return meatAndNonMeatEaters;
    }

    @Benchmark
    public PartitionMutableList<Animal> getMeatAndNonMeatEatersEc()
    {
        org.eclipse.collections.api.block.predicate.Predicate<Animal> eatsMeat = animal ->
                animal.getFavoriteFoods().anySatisfy(food -> food.getFoodType() == FoodType.MEAT);

        PartitionMutableList<Animal> meatAndNonMeatEaters = zooAnimals.partition(eatsMeat);
        //output = getSelected() = [[Tony, TIGER], [Simba, LION]]
        //output = getRejected() = [[ZigZag, ZEBRA], [Phil, GIRAFFE], [Lil, GIRAFFE]]
        return meatAndNonMeatEaters;
    }
}
