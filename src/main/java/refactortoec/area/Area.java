package refactortoec.area;

public class Area
{
    private double size;
    private Point center;
    private int boundaryPoints;

    public Area(double newSize, int newBoundaryPoints, Point newCenter)
    {
        this.size = newSize;
        this.boundaryPoints = newBoundaryPoints;
        this.center = newCenter;
    }

    public double getDiameter()
    {
        return this.size;
    }

    public Point getCenter()
    {
        return this.center;
    }

    public int getBoundaryPoints()
    {
        return this.boundaryPoints;
    }
}
