### Slide 1 - Title

### Slide 2 - Introduction

### Slide 3 - Any Types You Need
Eclipse Collections offers a tremendous variety of collection types, but you don't have to memorize them all. Just pay 
attention to the basic building blocks representing different collection types and their aspects.

### Slide 4 - Instantiate Them Using Factories
This is similar to Java 9 collection factory methods on List, Set, and Map interfaces, except these methods are a lot 
more comprehensive

### Slide 5 - Methods [some of] by Category
Rich APIs are available directly on the collection types themselves. 

### Slide 6 - Methods – Lots More…
Word sizes in this word cloud are proportional to the number of implementations of the method. There are multiple implementations of key 
methods on different collection types optimized for those specific types.

### Slide 7 - Why Refactor To EC?
**Performance** - We are pretty sure it will improve in most cases. We have our benchmarks but you really need to run 
yours.

### Slide 8 - Let's Do It!

#### Example: Word Count
Starting simple.

Given a text (in this case a nursery rhyme) count the number of occurrences of each word in the text. The result is a 
collection of words and numbers.

With the JDK collections we have to choose between efficient code or readable code. And in this case the reabable code 
is actually fairly readable, but really inefficient. So we will try to optimize it.

Eclipse Collection Bag offers a solution tailor made for this problem and the implementation that is optimized just for 
this particular collection type.

#### Example: Altcoin Report
We want to trade crypto currencies.

Given a list of crypto currencies and their average trading volume, we want to select currencies that show some
meaningful trading activity.

For that subset we want to request quotes that show the most recent price and price change. 

We filter what currencies we get prices for because it costs us to get real time quotes so we don't want to do it 
unnecessarily. 

For the quoted altcoins we want to pick the top four ones that showed the most change (up or down) because those are the ones 
where we are hoping to make the most money. 

We print the quote details on the console.

#### Example: Zoo
In a zoo we keep animals, who eat different kinds of food. 

We want to answer a few questions about the animals and the food they eat:

* Highest quantity food
* List of Animals and the number Of their favorite foods
* Unique foods
* Types of food
* find the meat and non meat eaters

These code snippets have also been tested with the Java Microbenchmark Harness (JMH) framework.
 We will go through the code examples and then take a look at how they compare.
 
Ex. 1 - Most popular food item
How in demand are our different food options? We need to keep track of what is being eaten the most.

Ex 2 - number of favorite food items to animal
How diverse is each animal's food choice? How many animals eat just one thing? How many eat two?

Ex 3 - unique foods
How many different types of food do we have? What are they?
- discuss target collections

Ex 4 - meat and non meat eaters
How many meat eaters do we have? How many non meat eaters?

Latest benchmark results:
Benchmark                                                  Mode  Cnt         Score        Error  Units
ZooBenchmarks.getMeatAndNonMeatEatersEc                   thrpt   40  10954950.120 ± 307316.411  ops/s
ZooBenchmarks.getMeatAndNonMeatEatersJdk                  thrpt   40   1950009.366 ±  45231.808  ops/s
ZooBenchmarks.mostPopularFoodItemEc                       thrpt   40   2258615.801 ± 126587.974  ops/s
ZooBenchmarks.mostPopularFoodItemJdk                      thrpt   40    859913.369 ±  26398.501  ops/s
ZooBenchmarks.printNumberOfFavoriteFoodItemsToAnimalsEc   thrpt   40   1044445.447 ±  29083.819  ops/s
ZooBenchmarks.printNumberOfFavoriteFoodItemsToAnimalsJdk  thrpt   40    867522.768 ±  67087.502  ops/s
ZooBenchmarks.uniqueFoodsEcWithTargetCollection           thrpt   40   6774968.971 ± 140500.361  ops/s
ZooBenchmarks.uniqueFoodsEcWithoutTargetCollection        thrpt   40   2535652.652 ±  67373.182  ops/s
ZooBenchmarks.uniqueFoodsJdk                              thrpt   40   2165134.848 ± 103500.261  ops/s


### Slide 9 - JMH Benchmark Results

### Slide 10 - Memory Usage Comparison
