package pathfinding;

import creatures.Ghost;
import map.Level;
import map.Tile;
import map.TileType;

import java.util.*;

public class PacmanBreadthSearch extends PathfindingAlgorithm
{
    private final AStar aStar;
    public PacmanBreadthSearch(Level m)
    {
        super(m);
        this.aStar = new AStar(m);
    }

    @Override
    public List<Tile> getPath(Tile x, Tile y)
    {
        if (x.getType() == TileType.WALL || y.getType() == TileType.WALL)
            return null;

        if (x.posEquals(y))
            return Arrays.asList(y);

        Level l = this.getLevel();
        List<Map<Tile, Tile>> list = new ArrayList<>();
        list.add(new HashMap<>());
        list.get(0).put(x, null);
        int s = 0;

        while (list.get(s).size() > 0)
        {
            if (list.get(s).containsKey(y))
            {
                List<Tile> res = new ArrayList<>();
                res.add(y);
                Tile tile = y;

                while (s > 0)
                {
                    tile = list.get(s).get(tile);
                    res.add(tile);
                    --s;
                }

                Collections.reverse(res);

                return res;
            }

            l = l.getCopy();

            for (Ghost ghost : this.getLevel().getGhosts())
                for (int i = 0; i < l.getWidth(); ++i)
                    for (int j = 0; j < l.getHeight(); ++j)
                        if (this.getLevel().getDistance(ghost.getPos(), l.getTile(i, j)) <= s + 1)
                            l.getTile(i, j).setType(TileType.WALL);

            list.add(new HashMap<>());

            for (Map.Entry<Tile, Tile> entry : list.get(s).entrySet())
            {
                List<Tile> sucessors = l.getNeighbors(entry.getKey());

                for (Tile tile : sucessors)
                    list.get(s + 1).put(tile, entry.getKey());
            }

            ++s;
        }

        return null;
    }
}
