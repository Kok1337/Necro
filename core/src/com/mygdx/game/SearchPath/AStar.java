package com.mygdx.game.SearchPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class AStar
{
    public int tempG;
    public int width;
    public int height;
    public int nodeSize;
    public boolean isClose;
    public Node curr;
    public Node start;
    public Node target;
    private Node saveNode;
    public Node[][] map;
    public ArrayList<Node> open;
    public ArrayList<Node> path;
    public ArrayList<Node> close;
    public ArrayList<Node> closeAllTime;
    public Texture box;
    public Texture closeNode;

    //принимает размеры в пикселях и самостоятельно вычисляет все как надо
    public AStar(int width, int height, int nodeSize)
    {
        this.width = width;
        this.height = height;
        this.nodeSize = nodeSize;
        path = new ArrayList<Node>();
        open = new ArrayList<Node>();
        close = new ArrayList<Node>();
        map = new Node[1 + width / nodeSize][1 + height / nodeSize];
        for (int i = 0; i < 1 + width / nodeSize; i++)
        {
            for (int j = 0; j < 1 + height / nodeSize; j++)
            {
                map[i][j] = new Node(i, j);
            }
        }
        closeAllTime = new ArrayList<Node>();
        closeAllTime.add(new Node(-1,-1));
        box = new Texture("box.png");
        closeNode = new Texture("close.png");
    }

    public ArrayList<Node> getPath(int playerX, int playerY, int targetX, int targetY)
    {
        isClose = false;
        resetAll(close);
        resetAll(open);
        close.clear();
        open.clear();
        path.clear();
        try {
            start = map[playerX / nodeSize][playerY / nodeSize];
            target = map[targetX / nodeSize][targetY / nodeSize];
        } catch (Exception e)
        {
            return path;
        }

        if (closeAllTime.contains(target))
        {
            isClose = true;
        }
        open.add(start);
        start.gCost = 0;
        start.fCost = start.fCost + start.getH(target);
        while (open.size() > 0)
        {
            curr = getMinFCost(open);
            if (curr == target)
                break;
            open.remove(curr);
            close.add(curr);
            for (Node neighbor : getNeighbors(curr))
            {
                if ((isClose || !closeAllTime.contains(neighbor)) && !close.contains(neighbor))
                {
                    if (Math.abs(neighbor.position.x - curr.position.x) + Math.abs(neighbor.position.y - curr.position.y) == 1)
                        tempG = curr.gCost + 10;
                    else
                        tempG = curr.gCost + 14;
                    if (!open.contains(neighbor) || tempG < neighbor.gCost)
                    {
                        neighbor.parent = curr;
                        neighbor.gCost = tempG;
                        neighbor.fCost = neighbor.gCost + neighbor.getH(target);
                    }
                    if (!open.contains(neighbor))
                        open.add(neighbor);
                }
            }
        }
        Node son = target;
        for (int i = 0; i < close.size(); i++)
        {
            if (son == start)
                break;
            son = son.parent;
            path.add(son);
        }
        arrayFlip(path);
        if (isClose)
        {
            for (Node node : path)
            {
                if (closeAllTime.contains(node))
                {
                    path = getPath(start.position.x * nodeSize,start.position.y * nodeSize,node.position.x * nodeSize,node.position.y * nodeSize);
                    //arrayFlip(path);
                    break;
                }
            }
        }


        return path;
    }

    //принимает координаты в нодах
    public Node getNodeFromMap(int x, int y)
    {
        try
        {
            if (saveNode == null)
                saveNode = map[x][y];
            return map[x][y];
        } catch (Exception e)
        {
            //Gdx.app.error("kok", e.toString());
            if (saveNode == null)
                return map[map.length - 1][map[map.length - 1].length - 1];
            else return saveNode;
        }

    }

    public void setCloseAllTime(ArrayList<Node> closeAllTime)
    {
        this.closeAllTime = closeAllTime;
    }

    public Node getMinFCost(ArrayList<Node> nodes)
    {
        int min = 0;
        for (int i = 1; i < nodes.size(); i++)
        {
            if (nodes.get(i).fCost < nodes.get(min).fCost)
                min = i;
        }
        return nodes.get(min);
    }

    public ArrayList<Node> getNeighbors(Node node)
    {
        ArrayList<Node> neighbors = new ArrayList<Node>(8);
        if (node.position.y + 1 < map[0].length)
            neighbors.add(map[node.position.x][node.position.y + 1]);

        if (node.position.y + 1 < map[0].length && node.position.x + 1 < map.length)
            neighbors.add(map[node.position.x + 1][node.position.y + 1]);

        if (node.position.x + 1 < map.length)
            neighbors.add(map[node.position.x + 1][node.position.y]);

        if (node.position.x + 1 < map.length && node.position.y - 1 >= 0)
            neighbors.add(map[node.position.x + 1][node.position.y - 1]);

        if (node.position.y - 1 >= 0)
            neighbors.add(map[node.position.x][node.position.y - 1]);

        if (node.position.y - 1 >= 0 && node.position.x - 1 >= 0)
            neighbors.add(map[node.position.x - 1][node.position.y - 1]);

        if (node.position.x - 1 >= 0)
            neighbors.add(map[node.position.x - 1][node.position.y]);

        if (node.position.y + 1 < map[0].length && node.position.x - 1 >= 0)
            neighbors.add(map[node.position.x - 1][node.position.y + 1]);

        return neighbors;
    }

    public void resetAll(ArrayList<Node> nodes)
    {
        for (Node n : nodes)
        {
            n.reset();
        }
    }

    public void render(SpriteBatch batch)
    {
        for (Node node : closeAllTime)
        {
            batch.draw(closeNode, node.position.x * nodeSize, node.position.y * nodeSize);
        }

        for (Node node : path)
        {
            batch.draw(box, node.position.x * nodeSize, node.position.y * nodeSize);
        }
    }

    public void arrayFlip(ArrayList<Node> nodes)
    {
        Node memory;
        for (int i = 0; i < nodes.size() / 2; i++)
        {
            memory = nodes.get(i);
            nodes.set(i, nodes.get(nodes.size() - i - 1));
            nodes.set(nodes.size() - i - 1, memory);
        }
    }
}

