package creatures;

import map.AStar;
import map.Map;
import map.Tile;
import map.TileType;

import java.util.List;
import java.util.Random;

public class Ghost
{
    private Map map;
    private AStar algorithm;
    private Tile pos;
    private List<Tile> path;

    public Ghost(Map m, Tile p)
    {
        this.map = m;
        this.algorithm = new AStar(this.map);
        this.pos = p;
        this.path = null;
        this.map.addGhost(this);
    }

    public Tile getPos()
    {
        return pos;
    }

    public void tick()
    {
        if (this.path != null)
        {
            if (this.path.size() == 1)
                this.path = null;
            else
            {
                this.path.remove(0);
                this.pos = this.path.get(0);
            }
        }
        else
        {
            Random random = new Random();
            this.path = this.algorithm.getPath(this.pos, this.map.getTile(random.nextInt(this.map.getWidth()), random.nextInt(this.map.getHeight())));

            if (path != null)
                for (Tile tile : this.path)
                    tile.setType(TileType.FOOD);
        }

        this.pos.setType(TileType.EMPTY);
    }
}
