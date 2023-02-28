package map;

import java.util.Objects;

public class Tile
{
    private final int x;
    private final int y;
    private TileType type;

    public Tile(int x, int y, TileType t)
    {
        this.x = x;
        this.y = y;
        this.type = t;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public TileType getType()
    {
        return type;
    }

    public void setType(TileType type)
    {
        this.type = type;
    }

    public boolean posEquals(Tile t)
    {
        return this.getX() == t.getX() && this.getY() == t.getY();
    }

    public double getDistance(Tile t)
    {
        return Math.sqrt((this.getX() - t.getX()) * (this.getX() - t.getX()) + (this.getY() - t.getY()) * (this.getY() - t.getY()));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y && type == tile.type;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, type);
    }
}
