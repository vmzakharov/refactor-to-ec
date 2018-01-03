package refactortoec.area;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.primitive.ObjectDoublePair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SelectAreas
{
    protected MutableList<Area> loadData()
    {
        Area a1 = new Area(100, 1000, new Point(100, 100));
        Area a2 = new Area(120, 1000, new Point(411, 415));
        Area a3 = new Area( 90, 1000, new Point(380,  80));
        Area a4 = new Area( 95, 1000, new Point(105, 405));

        MutableList<Area> areas = Lists.mutable.of(a1, a2, a3, a4);

        Random r = new Random(100);

        for (int i = 0; i < 100; i++)
        {
            areas.add(new Area(r.nextDouble()*50, r.nextInt(4) + 1, new Point(500, 500)));
        }

        return areas;
    }

    protected ObjectDoublePair<Point> computeCenterAndDiameter(Area a)
    {
        return PrimitiveTuples.pair(a.getCenter(), a.getDiameter());
    }

    public static void main(String[] args)
    {
        SelectAreas selectAreas = new SelectAreas();

        selectAreas.selectAreaEc();
    }

    public void selectAreaJdk()
    {
        List<Area> areas = this.loadData();

        List<Point> cornerCenters = areas
                .stream()
                .filter(area -> area.getBoundaryPoints() > 2)
                .map(this::computeCenterAndDiameter)
                .sorted(Comparator.comparingDouble(ObjectDoublePair::getTwo)) //.reversed
                .map(ObjectDoublePair::getOne)
                .limit(4)
                .collect(Collectors.toList());

        System.out.println(cornerCenters);
    }

    public void selectAreaEc()
    {
        ListIterable<Area> areas = this.loadData();
/*
        MutableList<CoordinatePoint> cornerCenters = cornerBoundaries
                .select(boundary -> boundary.size() > 2)
                .collect(this::findCenterAndDiameter)
                .sortThisByLong(pair -> -pair.getTwo())
                .take(4)
                .collect(ObjectLongPair::getOne);
 */
        MutableList<Point> cornerCenters = areas
                .select(area -> area.getBoundaryPoints() > 2)
                .collect(this::computeCenterAndDiameter)
                .toSortedListBy(ObjectDoublePair::getTwo)
                .reverseThis()
                .take(4)
                .collect(ObjectDoublePair::getOne);

        System.out.println(cornerCenters);
    }
}
