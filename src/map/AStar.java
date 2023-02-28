package map;

import java.util.*;

public class AStar
{
    private final Level level;

    public AStar(Level m)
    {
        this.level = m;
    }

    public List<Tile> getPath(Tile x, Tile y)
    {
        if (x.getType() == TileType.WALL || y.getType() == TileType.WALL)
            return null;

        if (x.posEquals(y))
            return Arrays.asList(y);

        PriorityQueue<ANode> openList = new PriorityQueue<>(new ANodeComparator());
        openList.add(new ANode(x, null, 0, AStar.getH(x, y)));
        ArrayList<ANode> closedList = new ArrayList<>();

        while (openList.size() > 0)
        {
            ANode node = openList.remove();
            List<Tile> successors = new ArrayList<>();

            if (node.getTile().getX() != 0)
                successors.add(this.level.getTile(node.getTile().getX() - 1, node.getTile().getY()));

            if (node.getTile().getY() != 0)
                successors.add(this.level.getTile(node.getTile().getX(), node.getTile().getY() - 1));

            if (node.getTile().getX() != this.level.getWidth() - 1)
                successors.add(this.level.getTile(node.getTile().getX() + 1, node.getTile().getY()));

            if (node.getTile().getY() != this.level.getHeight() - 1)
                successors.add(this.level.getTile(node.getTile().getX(), node.getTile().getY() + 1));

            for (Tile tile : successors)
            {
                if (tile.equals(y))
                {
                    List<Tile> res = new ArrayList<>();
                    res.add(y);
                    res.add(node.getTile());

                    while (node.getParent() != null)
                    {
                        node = node.getParent();
                        res.add(node.getTile());
                    }

                    Collections.reverse(res);

                    return res;
                }
                else if (tile.getType() != TileType.WALL)
                {
                    int g = node.getG() + 1;
                    int h = AStar.getH(tile, y);

                    boolean f = false;

                    for (ANode n : openList)
                        if (n.getTile().equals(tile) && n.getF() < g + h)
                        {
                            f = true;
                            break;
                        }

                    if (f)
                        continue;

                    for (ANode n : closedList)
                        if (n.getTile().equals(tile) && n.getF() < g + h)
                        {
                            f = true;
                            break;
                        }

                    if (f)
                        continue;

                    openList.add(new ANode(tile, node, g, h));
                }
            }

            closedList.add(node);
        }

        return null;
    }

    private static int getH(Tile x, Tile y)
    {
        int a = x.getX() - y.getX();
        int b = x.getY() - y.getY();

        if (a < 0)
            a = -a;

        if (b < 0)
            b = -b;

        return a + b;
    }
}

class ANode
{
    private final Tile tile;
    private final ANode parent;
    private final int g;
    private final int h;


    public ANode(Tile t, ANode p, int g, int h)
    {
        this.tile = t;
        this.parent = p;
        this.g = g;
        this.h = h;
    }

    public int getG()
    {
        return this.g;
    }

    public int getF()
    {
        return this.g + this.h;
    }

    public Tile getTile()
    {
        return tile;
    }

    public ANode getParent()
    {
        return parent;
    }
}

class ANodeComparator implements Comparator<ANode>
{
    @Override
    public int compare(ANode o1, ANode o2)
    {
        return o1.getF() - o2.getF();
    }
}
