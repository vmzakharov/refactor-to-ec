###Slide 1 - Title

###Slide 2 - Introduction

###Slide 3 - Any Types You Need
Eclipse Collections offers a tremendous variety of collection types, but you don't have to memorize them all. Just pay 
attention to the basic building blocks representing different collection types and their aspects.

###Slide 4 - Instantiate Them Using Factories
This is similar to Java 9 collection factory methods on List, Set, and Map interfaces, except these methods are a lot 
more comprehensive

###Slide 5 - Methods [some of] by Category
Rich APIs are available directly on the collection types themselves. 

###Slide 6 - Methods – Lots More…
Word sizes in this word cloud are proportional to the number of implementations of the method. There are multiple implementations of key 
methods on different collection types optimized for those specific types.

###Slide 7 - Why Refactor To EC?
**Performance** - We are pretty sure it will improve in most cases. We have our benchmarks but you really need to run 
yours.

###Slide 8 - Let's Do It!

####Example: Word Count
Starting simple.

Given a text (in this case a nursery rhyme) count the number of occurrences of each word in the text. The result is a 
collection of words and numbers.

With the JDK collections we have to choose between efficient code or readable code. And in this case the reabable code 
is actually fairly readable, but really inefficient. So we will try to optimize it.

Eclipse Collection Bag offers a solution tailor made for this problem and the implementation that is optimized just for 
this particular collection type.

####Example: Altcoin Report
We want to trade crypto currencies.

Given a list of crypto currencies and their average trading volume, we want to select currencies that show some
meaningful trading activity.

For that subset we want to request quotes that show the most recent price and price change. 

We filter what currencies we get prices for because it costs us to get real time quotes so we don't want to do it 
unnecessarily. 

For the quoted altcoins we want to pick the top four ones that showed the most change (up or down) because those are the ones 
where we are hoping to make the most money. 

We print the quote details on the console.

####Example: Zoo
In a zoo we keep animals, who eat different kinds of food. 

We want to answer a few questions about the animals and the food they eat:

* Highest quantity food
* List of Animals and the number Of their favorite foods
* Unique foods
* Types of food

###Slide 9 - JMH Benchmark Results

###Slide 10 - Memory Usage Comparison
