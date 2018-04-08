package com.mygdx.game.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Model.Passage;
import com.mygdx.game.Position;

public class Second extends World
{
    public Second(Game game) {
        super(game, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 20, new Position(700, 300));
    }

    @Override
    void getCloseNode()
    {
        for (int i = 3; i < 8; i++)
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
        passages.add(new Passage(700, 100, new Texture("door.png"), new First(this.game)));
    }

    @Override
    void getThing() {

    }
}
