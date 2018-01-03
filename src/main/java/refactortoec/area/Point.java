package refactortoec.area;

public class Point
{
    private double x;
    private double y;

    public Point(double newX, double newY)
    {
        this.x = newX;
        this.y = newY;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    @Override
    public String toString()
    {
        return "(" + this.x + ", " + this.y + ")";
    }
}
