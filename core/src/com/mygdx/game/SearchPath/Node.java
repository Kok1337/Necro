package com.mygdx.game.SearchPath;

import com.mygdx.game.Position;

public class Node
{
    public int gCost;
    public int fCost;
    public Node parent;
    public Position position;

    public Node(int x, int y)
    {
        position = new Position(x, y);
    }

    public int getH(Node node)
    {
        return Math.min(Math.abs(this.position.x - node.position.x), Math.abs(this.position.y - node.position.y)) * 14
                + (Math.max(Math.abs(this.position.x - node.position.x), Math.abs(this.position.y - node.position.y))
                - Math.min(Math.abs(this.position.x - node.position.x), Math.abs(this.position.y - node.position.y))) * 10;
    }

    public void reset()
    {
        this.parent = null;
        this.fCost = 0;
        this.gCost = 0;
    }
}
