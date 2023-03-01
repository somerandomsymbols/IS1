package creatures;

import map.Level;
import map.Tile;
import map.TileType;
import pathfinding.PacmanBreadthSearch;
import pathfinding.PathfindingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Pacman
{
    private Level level;
    private Tile pos;
    private int direction;
    private boolean isPlayerControlled;
    private List<Tile> path;

    public Pacman(Level m, Tile p)
    {
        this.level = m;
        this.pos = p;
        this.level.setPacman(this);
        this.path = null;
        this.isPlayerControlled = false;
    }

    public void tick()
    {
        this.pos.setType(TileType.EMPTY);

        if (this.isPlayerControlled)
        {
            int dx = 0;
            int dy = 0;

            switch (this.direction) {
                case 0:
                    dx = -1;
                    break;
                case 1:
                    dy = -1;
                    break;
                case 2:
                    dx = 1;
                    break;
                case 3:
                    dy = 1;
                    break;
            }

            int x = this.pos.getX() + dx;
            int y = this.pos.getY() + dy;

            if (x >= 0 && x < this.level.getWidth() && y >= 0 && y < this.level.getHeight()) {
                Tile d = level.getTile(x, y);

                if (d.getType() != TileType.WALL)
                    this.pos = d;
            }
        }
        else
        {
            if (this.path == null || this.path.size() == 1)
            {
                List<Tile> escapePath = new ArrayList<>();
                List<Tile> foodPath = new ArrayList<>();

                PathfindingAlgorithm algorithm = new PacmanBreadthSearch(this.level);

                for (int x = 0; x < this.level.getWidth(); ++x)
                    for(int y = 0; y < this.level.getHeight(); ++y)
                    {
                        List<Tile> p = algorithm.getPath(this.pos, this.level.getTile(x, y));

                        if (p != null)
                        {
                            if (this.level.getTile(x, y).getType() == TileType.FOOD)
                                if (foodPath.size() == 0 || foodPath.size() < p.size())
                                    foodPath = p;

                            if (escapePath.size() < p.size())
                                escapePath = p;
                        }
                    }

                if (escapePath.size() == 0 && foodPath.size() == 0)
                    this.path = null;
                else if (escapePath.size() == 0)
                    this.path = foodPath;
                else if (foodPath.size() == 0)
                    this.path = escapePath;
                else
                {
                    int d = level.getWidth() * level.getHeight();

                    for (Ghost ghost : level.getGhosts())
                    {
                        if (d < this.level.getDistance(this.pos, ghost.getPos()))
                            d = this.level.getDistance(this.pos, ghost.getPos());
                    }

                    if (d > 7)
                        this.path = foodPath;
                    else
                        this.path = escapePath;
                }
            }

            if (this.path == null || this.path.size() == 1)
                this.path = null;
            else
            {
                this.path.remove(0);

                Tile target = this.path.get(0);

                if (this.pos.getX() > target.getX())
                    this.direction = 0;
                else if (this.pos.getX() < target.getX())
                    this.direction = 2;
                else if (this.pos.getY() > target.getY())
                    this.direction = 1;
                else if (this.pos.getY() < target.getY())
                    this.direction = 3;

                this.pos = level.getTile(target.getX(), target.getY());
            }
        }
    }

    public Tile getPos()
    {
        return pos;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int d)
    {
        this.direction = d;
    }

    public boolean isPlayerControlled()
    {
        return isPlayerControlled;
    }

    public void setPlayerControlled(boolean playerControlled)
    {
        isPlayerControlled = playerControlled;
    }
}
