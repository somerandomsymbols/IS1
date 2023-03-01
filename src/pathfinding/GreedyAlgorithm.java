package pathfinding;

import map.Level;
import map.Tile;
import map.TileType;

import java.util.*;

public class GreedyAlgorithm extends PathfindingAlgorithm
{
    public GreedyAlgorithm(Level m)
    {
        super(m);
    }

    @Override
    public List<Tile> getPath(Tile x, Tile y)
    {
        if (x.getType() == TileType.WALL || y.getType() == TileType.WALL)
            return null;

        if (x.posEquals(y))
            return Arrays.asList(y);

        PriorityQueue<GraphNode> openList = new PriorityQueue<>(new GraphNodeComparator());
        List<GraphNode> closedList = new ArrayList<>();

        openList.add(new GraphNode(x, null, GreedyAlgorithm.calculateHeuristics(x, y)));

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
                    int h = GreedyAlgorithm.calculateHeuristics(tile, y);
                    boolean f = false;

                    for (GraphNode n : closedList)
                        if (n.getTile().posEquals(tile))
                        {
                            f = true;
                            break;
                        }

                    if (!f)
                        openList.add(new GraphNode(tile, node, h));
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
        private final int h;


        public GraphNode(Tile t, GraphNode p, int h)
        {
            this.tile = t;
            this.parent = p;
            this.h = h;
        }

        public int getH()
        {
            return this.h;
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
            return o1.getH() - o2.getH();
        }
    }
}
