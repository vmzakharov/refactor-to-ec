package refactortoec.wordcount;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCountTest
{
    private static List<String> words;

    @BeforeClass
    static public void loadData()
    {
        words = Arrays.asList(
                ("Bah, Bah, a black Sheep,\n" +
                "Have you any Wool?\n" +
                "Yes merry I Have,\n" +
                "Three Bags full,\n" +
                "Two for my Master,\n" +
                "One for my Dame,\n" +
                "None for the Little Boy\n" +
                "That cries in the lane").split("[ ,\n?]+")
        );
    }

/*
Humpty Dumpty sat on a wall,
Humpty Dumpty had a great fall.
All the king's horses and all the king's men
Couldn't put Humpty together again.

Rock-a-bye baby, on the treetop,
When the wind blows, the cradle will rock,
When the bough breaks, the cradle will fall,
And down will come baby, cradle and all.

There was an old woman who lived in a shoe.
She had so many children, she didn't know what to do;
She gave them some broth without any bread;
Then whipped them all soundly and put them to bed.
*/

    @Test
    public void countJdkNaive()
    {
        Map<String, Integer> wordCount = new HashMap<>();

        words.forEach(w -> {
            int count = wordCount.getOrDefault(w, 0);
            count++;
            wordCount.put(w, count);
        });

        System.out.println(wordCount);

        Assert.assertEquals(2, wordCount.get("Bah").intValue());
        Assert.assertEquals(3, wordCount.get("for").intValue());
        Assert.assertEquals(1, wordCount.get("Sheep").intValue());
    }


    @Test
    public void countJdkStream()
    {
        Map<String, Long> wordCounts = words.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        Assert.assertEquals(2, wordCounts.get("Bah").intValue());
        Assert.assertEquals(3, wordCounts.get("for").intValue());
        Assert.assertEquals(1, wordCounts.get("Sheep").intValue());
    }

    @Test
    public void countJdkEfficient()
    {
        Map<String, Counter> wordCounts = new HashMap<>();

        words.forEach(
                w -> {
                    Counter counter = wordCounts.get(w);
                    if (counter == null)
                    {
                        counter = new Counter();
                        wordCounts.put(w, counter);
                    }
                    counter.increment();
                }
        );

        System.out.println(wordCounts);

        Assert.assertEquals(2, wordCounts.get("Bah").intValue());
        Assert.assertEquals(3, wordCounts.get("for").intValue());
        Assert.assertEquals(1, wordCounts.get("Sheep").intValue());
    }

    public class Counter
    {
        private int value = 0;

        public void increment()
        {
            this.value++;
        }

        public int intValue()
        {
            return this.value;
        }
    }

    @Test
    public void countEc()
    {
        ImmutableList<String> wordList = Lists.immutable.ofAll(words);

        MutableBag<String> bagOfWords = wordList.toBag();

//        bagOfWords.forEachWithOccurrences((word, count) -> System.out.println(word + ": " + count));

        Assert.assertEquals(2, bagOfWords.occurrencesOf("Bah"));
        Assert.assertEquals(3, bagOfWords.occurrencesOf("for"));
        Assert.assertEquals(1, bagOfWords.occurrencesOf("Sheep"));
        Assert.assertEquals(0, bagOfWords.occurrencesOf("Cheburashka"));
    }
}
