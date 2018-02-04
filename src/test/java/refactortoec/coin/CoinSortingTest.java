package refactortoec.coin;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CoinSortingTest
{
    private static ListIterable<CryptoCcy> cryptoCcyAsListIterable;
    private static List<CryptoCcy> cryptoCcyAsJdkList;
    private static MutableMap<String, CoinPriceQuote> quotesBySymbol;

    @BeforeClass
    public static void loadData()
    {
        cryptoCcyAsListIterable = Lists.mutable.of(
                new CryptoCcy("Bitcoin", "BTC", 1_300_000), // 17_000 +1%
                new CryptoCcy("Dogecoin", "DOGE", 20_000), // 0.015 +40%
                new CryptoCcy("Ethereum", "ETH", 300_000), // 1000 + 3%
                new CryptoCcy("Ripple", "XRP", 200_000), // 3 -5%
                new CryptoCcy("Litecoin", "LTC", 135_000), // 300 +20#
                new CryptoCcy("Einsteinium", "EMC2", 850), // 1 +2%
                new CryptoCcy("IOTA", "MIOTA", 9_500), // 4 -10%
                new CryptoCcy("Selfiecoin", "SLFI", 0.01), // 0.0002 +2%
                new CryptoCcy("Dreamcoin", "DRM", 0.000001) // 0.05 -10%
        );

        cryptoCcyAsJdkList = (List<CryptoCcy>) cryptoCcyAsListIterable;

        ListIterable<CoinPriceQuote> quotes = Lists.immutable.of(
                new CoinPriceQuote("BTC",  17_000.0,    1.0),
                new CoinPriceQuote("DOGE",      0.015, 40.0),
                new CoinPriceQuote("ETH",   1_000.0,    3.0),
                new CoinPriceQuote("XRP",       3.0,   -5.0),
                new CoinPriceQuote("LTC",     300.0,   20.0),
                new CoinPriceQuote("EMC2",      1.0,    2.0),
                new CoinPriceQuote("MIOTA",     4.0,  -10.0),
                new CoinPriceQuote("SLFI",      0.0002, 2.0),
                new CoinPriceQuote("DRM",       0.05, -10.0));

        quotesBySymbol = quotes.toMap(CoinPriceQuote::getSymbol, quote -> quote);
    }

    public static ListIterable<CryptoCcy> getCryptoCcyAsListIterable()
    {
        return cryptoCcyAsListIterable;
    }

    public static List<CryptoCcy> getCryptoCcyAsJdkList()
    {
        return cryptoCcyAsJdkList;
    }

    @Test
    public void filerCoinsJdk()
    {
        List<CoinPriceQuote> interestingCoins = getCryptoCcyAsJdkList()
                .stream()
                .filter(cryptoCcy -> cryptoCcy.getAvgDailyVolume() > 1)
                .map(this::getPriceQuote)
                .sorted(Comparator.comparingDouble(CoinPriceQuote::getAbsPercentChange).reversed())
                .limit(4)
                .collect(Collectors.toList());

        System.out.println(interestingCoins
                                   .stream()
                                   .map(CoinPriceQuote::toString)
                                   .collect(Collectors.joining("\n", "JDK\n", "\n-----")));

        Assert.assertArrayEquals(new String[] {"DOGE", "LTC", "MIOTA", "XRP"},
                                 interestingCoins
                                         .stream()
                                         .map(CoinPriceQuote::getSymbol)
                                         .toArray());
    }

    @Test
    public void filerCoinsEc()
    {
        MutableList<CoinPriceQuote> interestingCoins = getCryptoCcyAsListIterable()
                .select(cryptoCcy -> cryptoCcy.getAvgDailyVolume() > 1)
                .collect(this::getPriceQuote)
                .toSortedList(Comparators.byDoubleFunction(CoinPriceQuote::getAbsPercentChange).reversed())
                .take(4);

        System.out.println(interestingCoins.makeString("EC\n", "\n", "\n-----"));

        Verify.assertIterablesEqual(
                Lists.immutable.of("DOGE", "LTC", "MIOTA", "XRP"),
                interestingCoins.collect(CoinPriceQuote::getSymbol));
    }

    private CoinPriceQuote getPriceQuote(CryptoCcy cryptoCcy)
    {
        return quotesBySymbol.get(cryptoCcy.getSymbol());
    }
}
