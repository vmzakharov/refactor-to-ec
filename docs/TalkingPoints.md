### Slide 1 - Title
Today we'll see how Eclipse Collections can help you optimize your Java code. Optimize not
just from the point of view of memory or performance, but also by making your code cleaner 
and easier to understand.

The main part of this talk is a live demo where we will see how to change the code that 
uses the standard Java collections and streams to code that uses Eclipse Collection 
framework. But before we dive into the code, we'll spend a few minutes to understand 
what EC is, why it is needed, and why you might want to rewrite a working idiomatic Java 
code to code using EC.

### Slide 2 - Introduction
Eclipse Collections is, in my opinion, the best collection library for Java, compatible 
with the collections that you get from the JDK.

Where did it all come from? EC was created at Goldman Sachs, initially for an application with a
very large cache component. The system stores hundreds of gigabytes of data in memory.
What is a cache? In fact a cache is simply a map, we write down objects there and get them 
out. And these objects can contain other maps, and lists of objects and so on. Initially, 
the cache was based on the standard collections from the package java.util. *. But it quickly 
became apparent that these collections have two significant drawbacks: inefficient use of 
memory, and very limited interfaces. It was not possible to patch it with utility libraries,
so to kill two birds with one stone a decision was made to rewrite it from scratch.
 
It was of course a somewhat radical solution, but it worked. Now this framework is under 
the umbrella of Eclipse Foundation.

On this slide you see the links to sites that will help you learn more about the project 
itself and ways to leard EC and contribute to it.

### Slide 3 - Why Refactor To EC?
So what are the benefits of EC? Why would you want to rewrite a good working piece of code 
already written in Java to idiomatic EC?

Before we talk directly about the benefits, it's important to note that moving to the EC 
is easy, and you don't have to do it all at once. Eclipse Collections includes fully 
compatible with JDK java.util. * implementations of List, Set and Map interfaces and is 
compatible with libraries from JDK, for example, Collectors.

But these familiar types in the EC have much richer interfaces. There are also additional 
types that are not in JDK, such as Bag, Multimap and BiMap. BiMap is an "inverted" map, 
you can find the key by value. Multimap - dictionaries with non-unique keys or multiple 
values for the same key. And a Bag is a multiset, a set with repeating elements.

Eclipse Collections also contains a complete collection of primitive types.

For each collection there is an immutable equivalent, which allows you to write a more 
secure code, where errors are caught by the compiler.

In the course of this demonstration, you will see how the code, which is written using a 
standard approach, is transformed to Eclipse Collections' types and methods, and how this 
in turn will lead to significant memory savings. And depending on your particular scenario, 
it may also lead to improved performance.

Eclipse Collections allows you to easily switch from lazy to eager implementation of 
collections and back, which greatly helps writing, understanding and debugging of 
functional code in Java.

Immutable collections allow you to develop more correct code, without changing the state 
of the contents of collections. The correctness of the program in this case will be 
guaranteed by the compiler, which will avoid surprises during its execution. 
The combination of immutable collections and richer interfaces will allow you to write 
pure functional code in Java.

What are bun methods? This is an analogy, which was invented by Brian Goetz, chief architect 
of Java at Oracle. If you want meat, but you are given a hamburger, then there is a meat patty
between the two buts. These buns are empty calories, which you do not really need. Same with 
Java streams: if you want to do something, does not matter what, of course you need the methods that 
do what you actually want. But you also need to put these methods between two buns - the 
stream (or parallelStream) method at the beginning and the collector at the end. In EC these 
methods are not required.

### Slide 4 - Any Types You Need
In the EC there are types and methods for every need and they are easy to find by 
the features you require. So it is not necessary to memorize their individual names. 
Do you need a collection that is mutable or immutable? Sorted? What type of data do we 
want to store in the collection? All primitive types are supported. And finally, what kind 
of collection do you need? We can make these collections lazy or eager. In Java, streams 
are only lazy. We can also make collections parallel.

So do not memorize individual type names, just remember the the basic building blocks 
representing different collection types and their aspects.

### Slide 5 - Instantiate Them Using Factories
This is similar to Java 9 collection factory methods on List, Set, and Map interfaces, except these methods are a lot 
more comprehensive

### Slide 6 - Methods [some of] by Category
Rich APIs are available directly on the collection types themselves. 

### Slide 7 - Methods – Lots More…
Word sizes in this word cloud are proportional to the number of implementations of the method. There are multiple implementations of key 
methods on different collection types optimized for those specific types.

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
ZooBenchmarks.getMeatAndNonMeatEatersEc                   thrpt   40  15404986.588 ± 308084.554  ops/s
ZooBenchmarks.getMeatAndNonMeatEatersJdk                  thrpt   40   2293012.428 ±  23878.929  ops/s
ZooBenchmarks.mostPopularFoodItemEc                       thrpt   40   2983891.901 ±  33548.931  ops/s
ZooBenchmarks.mostPopularFoodItemJdk                      thrpt   40   1222743.182 ±   7744.237  ops/s
ZooBenchmarks.printNumberOfFavoriteFoodItemsToAnimalsEc   thrpt   40   1431644.010 ±  17279.903  ops/s
ZooBenchmarks.printNumberOfFavoriteFoodItemsToAnimalsJdk  thrpt   40   1347812.173 ±   8287.517  ops/s
ZooBenchmarks.uniqueFoodsEcWithTargetCollection           thrpt   40   9077488.911 ±  93978.941  ops/s
ZooBenchmarks.uniqueFoodsEcWithoutTargetCollection        thrpt   40   3558312.000 ±  26025.030  ops/s
ZooBenchmarks.uniqueFoodsJdk                              thrpt   40   2770848.646 ±  23794.867  ops/s

### Slide 9 - JMH Benchmark Results

### Slide 10 - Memory Usage Comparison
