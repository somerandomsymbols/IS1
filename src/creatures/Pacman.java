package creatures;

import map.Level;
import map.Tile;
import map.TileType;

public class Pacman
{
    private Level level;

    private Tile pos;

    public Pacman(Level m, Tile p)
    {
        this.level = m;
        this.pos = p;
        this.level.setPacman(this);
    }

    public boolean move(int dx, int dy)
    {
        int x = this.pos.getX() + dx;
        int y = this.pos.getY() + dy;

        if (x >= 0 && x < this.level.getWidth() && y >= 0 && y < this.level.getHeight() && this.level.getTile(x, y).getType() != TileType.WALL)
        {
            Tile d = level.getTile(x, y);

            if (d.getType() != TileType.WALL)
            {
                this.pos = d;
                this.pos.setType(TileType.EMPTY);
                return true;
            }
        }

        return false;
    }

    public Tile getPos()
    {
        return pos;
    }
}
