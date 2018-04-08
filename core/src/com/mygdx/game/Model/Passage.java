package com.mygdx.game.Model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Player.Player;
import com.mygdx.game.Position;

//Данный класс создает переход от одной локации на другую
public class Passage extends JustObject
{
    public Screen location;

    public Passage(int x, int y, Texture img, Screen location)
    {
        super(x, y, img);
        this.location = location;
    }

    public void openLocation(Game game)
    {
        game.setScreen(location);
    }
}
