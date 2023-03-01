package pathfinding;

import map.Level;
import map.Tile;
import map.TileType;

import java.util.*;

public class AStar extends PathfindingAlgorithm
{
    public AStar(Level m)
    {
        super(m);
    }

    public List<Tile> getPath(Tile x, Tile y)
    {
        if (x.getType() == TileType.WALL || y.getType() == TileType.WALL)
            return null;

        if (x.posEquals(y))
            return Arrays.asList(y);

        PriorityQueue<GraphNode> openList = new PriorityQueue<>(new GraphNodeComparator());
        List<GraphNode> closedList = new ArrayList<>();

        openList.add(new GraphNode(x, null, 0, AStar.calculateHeuristics(x, y)));

        while (openList.size() > 0)
        {
            GraphNode node = openList.remove();
            List<Tile> successors = this.getLevel().getNeighbors(node.getTile());

            for (Tile tile : successors)
            {
                if (tile.posEquals(y))
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
                else
                {
                    int g = node.getG() + 1;
                    int h = AStar.calculateHeuristics(tile, y);
                    boolean f = false;

                    for (GraphNode n : openList)
                        if (n.getTile().posEquals(tile) && n.getF() < g + h)
                        {
                            f = true;
                            break;
                        }

                    if (f)
                        continue;

                    for (GraphNode n : closedList)
                        if (n.getTile().posEquals(tile) && n.getF() < g + h)
                        {
                            f = true;
                            break;
                        }

                    if (f)
                        continue;

                    openList.add(new GraphNode(tile, node, g, h));
                }
            }

            closedList.add(node);
        }

        return null;
    }

    private static int calculateHeuristics(Tile x, Tile y)
    {
        return PathfindingAlgorithm.getDistance(x, y);
    }

    private class GraphNode
    {
        private final Tile tile;
        private final GraphNode parent;
        private final int g;
        private final int h;


        public GraphNode(Tile t, GraphNode p, int g, int h)
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

        public GraphNode getParent()
        {
            return parent;
        }
    }

    private class GraphNodeComparator implements Comparator<GraphNode>
    {
        @Override
        public int compare(GraphNode o1, GraphNode o2)
        {
            return o1.getF() - o2.getF();
        }
    }
}