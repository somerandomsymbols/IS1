package creatures;

import main.Main;
import map.AStar;
import map.Level;
import map.Tile;

import java.util.List;
import java.util.Random;

public class Ghost
{
    private Level level;
    private AStar algorithm;
    private Tile pos;
    private List<Tile> path;
    private Tile restPos;
    private GhostBehaviour behaviour;
    private int timeout = 0;
    private int direction = 0;
    private GhostColor color;

    public Ghost(Level m, Tile p, GhostColor c)
    {
        this.level = m;
        this.algorithm = new AStar(this.level);
        this.pos = p;
        this.path = null;
        this.restPos = p;//this.map.getTile(this.map.getWidth() / 2, this.map.getHeight() / 2);
        this.behaviour = GhostBehaviour.RANDOM;
        this.color = c;
        this.timeout = 0;
        this.level.addGhost(this);
    }

    public Tile getPos()
    {
        return pos;
    }

    public int getDirection()
    {
        return direction;
    }

    public GhostColor getColor()
    {
        return color;
    }

    public GhostBehaviour getBehaviour()
    {
        return behaviour;
    }

    public void tick()
    {
        Random random = new Random();
        --this.timeout;

        if (this.timeout <= 0)
        {
            this.path = null;

            if (this.behaviour != GhostBehaviour.REST)
            {
                this.timeout += (5 + random.nextInt(6)) * Main.gameplayTicksPerSecond;
                this.behaviour = GhostBehaviour.REST;
            }
            else
            {
                this.timeout += (10 + random.nextInt(11)) * Main.gameplayTicksPerSecond;
                this.behaviour = GhostBehaviour.RANDOM;
            }
        }

        if (this.level.getPacman() != null)
        {
            if (this.level.getPacman().getPos().posEquals(this.pos))
                this.level.setPacman(null);

            if (this.behaviour == GhostBehaviour.RANDOM && this.level.getPacman().getPos().getDistance(this.pos) <= 5)
            {
                this.behaviour = GhostBehaviour.ATTACK;
                this.timeout = 10 * Main.gameplayTicksPerSecond;
            }
        }
        else
        {
            if (this.behaviour == GhostBehaviour.ATTACK)
                this.behaviour = GhostBehaviour.RANDOM;
        }

        /*if (this.behaviour == GhostBehaviour.REST && this.getPos().posEquals(this.restPos))
            this.behaviour = GhostBehaviour.RANDOM;*/

        if (this.path == null)
        {
            switch (this.behaviour)
            {
                case RANDOM:
                    this.path = this.algorithm.getPath(this.pos, this.level.getTile(
                            2 * random.nextInt(this.level.getWidth() / 2 + 1),
                            2 * random.nextInt(this.level.getHeight() / 2 + 1)
                    ));
                    break;
                case REST:
                    this.path = this.algorithm.getPath(this.pos, this.restPos);
                    break;
                case ATTACK:
                    this.path = this.algorithm.getPath(this.pos, this.level.getPacman().getPos());
                    break;
            }
        }

        if (this.path.size() == 1)
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

            this.pos = target;

            if (this.behaviour == GhostBehaviour.ATTACK)
                this.path = null;

            if (this.level.getPacman() != null && this.level.getPacman().getPos().posEquals(this.pos))
                this.level.setPacman(null);
        }

        /*if (path != null)
            for (Tile tile : this.path)
                tile.setType(TileType.FOOD);*/
        //this.pos.setType(TileType.EMPTY);
    }
}
