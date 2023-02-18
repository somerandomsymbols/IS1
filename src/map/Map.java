package map;

import creatures.Ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private final int width;
    private final int height;
    private Tile [][] map;
    private List<Ghost> ghosts;

    public Map(int w, int h)
    {
        this.ghosts = new ArrayList<>();
        this.width = w;
        this.height = h;
        this.map = new Tile[w][h];
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Tile getTile(int x, int y)
    {
        return this.map[x][y];
    }

    public List<Ghost> getGhosts()
    {
        return this.ghosts;
    }

    public void addGhost(Ghost g)
    {
        this.ghosts.add(g);
    }

    public static Map getRandom()
    {
        Random random = new Random(1488);
        int w = 15, h = 15;
        Map m = new Map(w, h);

        for (int x = 0; x < w; ++x)
            for (int y = 0; y < h; ++y)
            {
                TileType t;

                switch (random.nextInt(10))
                {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        t = TileType.EMPTY;
                        break;
                        case 7:
                    case 8:
                    case 9:
                        t = TileType.WALL;
                        break;
                    default:
                        t = TileType.FOOD;
                }

                if (x == 0 && y == 0)
                    t = TileType.EMPTY;

                if (x == w - 1 && y == w - 1)
                    t = TileType.EMPTY;

                m.map[x][y] = new Tile(x, y, t);
            }

        return m;
    }
}
