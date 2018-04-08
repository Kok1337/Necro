package com.mygdx.game.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Model.Passage;
import com.mygdx.game.Model.Thing;
import com.mygdx.game.Position;

/**
 * Created by Алексей on 08.04.2018.
 */

public class First extends World
{

    public First(Game game)
    {
        super(game, 1000, 1000, 20, new Position(0, 0));
    }

    @Override
    void getCloseNode()
    {
        for (int i = 5; i < 10; i++)
        {
            for (int j = 5; j < 10; j++)
            {
                closeAllTime.add(aStar.getNodeFromMap(i,j));
            }
        }
    }

    @Override
    void getPassage()
    {
        passages.add(new Passage(400, 100, new Texture("door.png"), new Second(this.game)));
    }

    @Override
    void getThing()
    {
        things.add(new Thing(10, 10, new Texture("obj.png")));
        things.add(new Thing(700, 400, new Texture("obj.png")));
    }
}
