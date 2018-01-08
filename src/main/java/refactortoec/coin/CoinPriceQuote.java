package refactortoec.coin;

public class CoinPriceQuote
{
    private String symbol;
    private double pirceUsd;
    private double percentChange;

    public CoinPriceQuote(String newSymbol, double newPirceUsd, double newPercentChange)
    {
        this.symbol = newSymbol;
        this.pirceUsd = newPirceUsd;
        this.percentChange = newPercentChange;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public double getPirceUsd()
    {
        return this.pirceUsd;
    }

    public double getPercentChange()
    {
        return this.percentChange;
    }

    public double getAbsPercentChange()
    {
        return Math.abs(this.percentChange);
    }

    @Override
    public String toString()
    {
        return String.format("%8s %,12.4f USD %,8.2f%%", this.symbol, this.pirceUsd, this.percentChange);
    }
}
