package map;

import creatures.Ghost;
import creatures.Pacman;
import pathfinding.AStar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private final int width;
    private final int height;
    private Tile [][] level;
    private List<Ghost> ghosts;
    private Pacman pacman;

    private int [][][][] distances;

    public Level(int w, int h)
    {
        this.ghosts = new ArrayList<>();
        this.width = w;
        this.height = h;
        this.level = new Tile[w][h];
        this.distances = null;
        Random random = new Random();

        for (int x = 0; x < this.width; ++x)
            for (int y = 0; y < this.height; ++y)
                this.level[x][y] = new Tile(x, y, random.nextBoolean() ? TileType.FOOD : TileType.EMPTY);
    }

    public int getDistance(Tile t1, Tile t2)
    {
        if (this.distances == null)
        {
            AStar aStar = new AStar(this);
            this.distances = new int[this.width][this.height][this.width][this.height];

            for (int x = 0; x < this.width; ++x)
                for (int y = 0; y < this.height; ++y)
                    for (int x1 = 0; x1 < this.width; ++x1)
                        for (int y1 = 0; y1 < this.height; ++y1)
                        {
                            List<Tile> p = aStar.getPath(this.level[x][y], this.level[x1][y1]);

                            this.distances[x1][y1][x][y] = p != null ? p.size() - 1 : this.width * this.height;
                        }
        }

        return this.distances[t1.getX()][t1.getY()][t2.getX()][t2.getY()];
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
        return this.level[x][y];
    }

    public List<Tile> getNeighbors(Tile t)
    {
        List<Tile> neighbors = new ArrayList<>();

        if (t.getX() != 0 && this.getTile(t.getX() - 1, t.getY()).getType() != TileType.WALL)
            neighbors.add(this.getTile(t.getX() - 1, t.getY()));

        if (t.getY() != 0 && this.getTile(t.getX(), t.getY() - 1).getType() != TileType.WALL)
            neighbors.add(this.getTile(t.getX(), t.getY() - 1));

        if (t.getX() != this.getWidth() - 1 && this.getTile(t.getX() + 1, t.getY()).getType() != TileType.WALL)
            neighbors.add(this.getTile(t.getX() + 1, t.getY()));

        if (t.getY() != this.getHeight() - 1 && this.getTile(t.getX(), t.getY() + 1).getType() != TileType.WALL)
            neighbors.add(this.getTile(t.getX(), t.getY() + 1));

        return neighbors;
    }

    public List<Ghost> getGhosts()
    {
        return this.ghosts;
    }

    public void addGhost(Ghost g)
    {
        this.ghosts.add(g);
    }

    public Pacman getPacman()
    {
        return this.pacman;
    }

    public void setPacman(Pacman p)
    {
        this.pacman = p;
    }

    public Level getCopy()
    {
        Level l = new Level(this.width, this.height);

        for (int x = 0; x < this.width; ++x)
            for (int y = 0; y < this.height; ++y)
                l.getTile(x, y).setType(this.getTile(x, y).getType());

        return l;
    }

    public static Level generate(int seed)
    {
        Random random = new Random(seed);

        int i = 7;
        Level l = new Level(i, i);

        l.getTile(1,1).setType(TileType.WALL);
        l.getTile(2,1).setType(TileType.WALL);
        l.getTile(3,1).setType(TileType.EMPTY);
        l.getTile(4,1).setType(TileType.WALL);
        l.getTile(5,1).setType(TileType.WALL);

        l.getTile(1,2).setType(TileType.WALL);
        l.getTile(2,2).setType(TileType.EMPTY);
        l.getTile(3,2).setType(TileType.EMPTY);
        l.getTile(4,2).setType(TileType.EMPTY);
        l.getTile(5,2).setType(TileType.WALL);

        l.getTile(1,3).setType(TileType.EMPTY);
        l.getTile(2,3).setType(TileType.EMPTY);
        l.getTile(3,3).setType(TileType.EMPTY);
        l.getTile(4,3).setType(TileType.EMPTY);
        l.getTile(5,3).setType(TileType.EMPTY);

        l.getTile(1,4).setType(TileType.WALL);
        l.getTile(2,4).setType(TileType.EMPTY);
        l.getTile(3,4).setType(TileType.EMPTY);
        l.getTile(4,4).setType(TileType.EMPTY);
        l.getTile(5,4).setType(TileType.WALL);

        l.getTile(1,5).setType(TileType.WALL);
        l.getTile(2,5).setType(TileType.WALL);
        l.getTile(3,5).setType(TileType.EMPTY);
        l.getTile(4,5).setType(TileType.WALL);
        l.getTile(5,5).setType(TileType.WALL);

        while (i < 23)
        {
            Level res = new Level(i + 4, i + 4);

            for (int x = 0; x < i; ++x)
                for (int y = 0; y < i; ++y)
                    res.getTile(x + 2, y + 2).setType(l.getTile(x, y).getType());

            i += 4;

            for (int j = 1; j < i; j += 2)
            {
                res.getTile(j, 1).setType(TileType.WALL);
                res.getTile(j, i - 2).setType(TileType.WALL);
                res.getTile(1, j).setType(TileType.WALL);
                res.getTile(i - 2, j).setType(TileType.WALL);
            }

            for (int j = 2; j < i - 1; j += 2)
            {
                if (random.nextInt(101) <= 50)
                    res.getTile(j, 1).setType(TileType.WALL);

                if (random.nextInt(101) <= 50)
                    res.getTile(j, i - 2).setType(TileType.WALL);

                if (random.nextInt(101) <= 50)
                    res.getTile(1, j).setType(TileType.WALL);

                if (random.nextInt(101) <= 50)
                    res.getTile(i - 2, j).setType(TileType.WALL);
            }

            l = res;
        }

        return l;
    }

    public static Level getRandom()
    {
        Random random = new Random();
        int w = 15, h = 15;
        Level l = new Level(w, h);

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

                l.level[x][y] = new Tile(x, y, t);
            }

        return l;
    }
}
