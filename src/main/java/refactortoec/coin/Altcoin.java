package refactortoec.coin;

public class Altcoin
{
    private final String name;
    private final String symbol;
    private final double avgDailyVolume;

    public Altcoin(String newName, String newSymbol, double newAvgDailyVolume)
    {
        this.name = newName;
        this.symbol = newSymbol;
        this.avgDailyVolume = newAvgDailyVolume;
    }

    public double getAvgDailyVolume()
    {
        return this.avgDailyVolume;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    @Override
    public String toString()
    {
        return this.name + " (" + symbol + ')';
    }
}

