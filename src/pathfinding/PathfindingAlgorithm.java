package pathfinding;

import map.Level;
import map.Tile;

import java.util.List;

public abstract class PathfindingAlgorithm
{
    private final Level level;

    public PathfindingAlgorithm(Level m)
    {
        this.level = m;
    }

    public final Level getLevel()
    {
        return level;
    }

    public static int getDistance(Tile x, Tile y)
    {
        int a = x.getX() - y.getX();
        int b = x.getY() - y.getY();

        if (a < 0)
            a = -a;

        if (b < 0)
            b = -b;

        return a + b;
    }

    public abstract List<Tile> getPath(Tile x, Tile y);
}
