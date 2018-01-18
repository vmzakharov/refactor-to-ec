package refactortoec.memorytest;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.*;

public class MemoryTestRunner
{
    private MemoryRecorder memoryRecorder = new MemoryRecorder();

    public static void main(String[] args)
    {
        MemoryTestRunner memoryTestRunner = new MemoryTestRunner();

        memoryTestRunner.runTests();
    }

    private void runTests()
    {
        this.memoryForEcSet();
        this.memoryForJdkSet();

//        this.memoryForEcMap();
//        this.memoryForJdkMap();
//
//        this.memoryForEcList();
//        this.memoryForJdkList();
//
//        this.memoryForEcBag();
//        this.memoryForJdkBag();
    }

    private void memoryForEcSet()
    {
        MutableSet<String> ecSet = Sets.mutable.of();
        this.memoryRecorder.recordMemoryUsageString(ecSet, ecSet::size, ecSet::add);
    }

    private void memoryForJdkSet()
    {
        Set<String> jdkSet = new HashSet<>();
        this.memoryRecorder.recordMemoryUsageString(jdkSet, jdkSet::size, jdkSet::add);
    }

    private void memoryForEcMap()
    {
        MutableMap<String, String> ecMap = Maps.mutable.of();
        this.memoryRecorder.recordMemoryUsageString(ecMap, ecMap::size, s -> ecMap.put(s, s));
    }

    private void memoryForJdkMap()
    {
        Map<String, String> jdkMap = new HashMap<>();
        this.memoryRecorder.recordMemoryUsageString(jdkMap, jdkMap::size, s -> jdkMap.put(s, s));
    }

    private void memoryForEcList()
    {
        MutableIntList ecList = IntLists.mutable.of();
        this.memoryRecorder.recordMemoryUsageInt(ecList, ecList::size, ecList::add);
    }

    private void memoryForJdkList()
    {
        List<Integer> jdkList = new ArrayList<>();
        this.memoryRecorder.recordMemoryUsageInt(jdkList, jdkList::size, jdkList::add);
    }

    private void memoryForEcBag()
    {
        MutableBag<String> ecBag = Bags.mutable.of();
        this.memoryRecorder.recordMemoryUsageString(ecBag, ecBag::size, ecBag::add);
    }

    private void memoryForJdkBag()
    {
        Map<String, Integer> jdkBag = new HashMap<>();
        this.memoryRecorder.recordMemoryUsageString(
                jdkBag,
                jdkBag::size,
                s -> {
                    int count = jdkBag.getOrDefault(s, 0);
                    count++;
                    jdkBag.put(s, count);
                });
    }
}
