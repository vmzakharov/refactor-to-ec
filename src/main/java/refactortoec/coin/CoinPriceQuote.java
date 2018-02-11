package refactortoec.coin;

public class CoinPriceQuote
{
    private String symbol;
    private double priceUsd;
    private double percentChange;

    public CoinPriceQuote(String newSymbol, double newPriceUsd, double newPercentChange)
    {
        this.symbol = newSymbol;
        this.priceUsd = newPriceUsd;
        this.percentChange = newPercentChange;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public double getPriceUsd()
    {
        return this.priceUsd;
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
        return String.format("%8s %,12.4f USD %,8.2f%%", this.symbol, this.priceUsd, this.percentChange);
    }
}
