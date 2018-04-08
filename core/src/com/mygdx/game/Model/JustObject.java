package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Player.Player;
import com.mygdx.game.Position;

public class JustObject
{
    public Position position;
    public Texture texture;
    public Rectangle body;
    public int id;
    public static int counter = 0;

    public JustObject(int x, int y, Texture img)
    {
        counter++;
        position = new Position(x, y);
        this.texture = img;
        this.body = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.id = counter;
    }

    public boolean isCollision(Player player)
    {
        return player.body.overlaps(this.body);
    }

    public int getIDByClick(int x, int y)
    {
        if (x >= position.x && x <= position.x + body.getWidth() && y >= position.y && y <= position.y + body.getHeight())
            return this.id;
        else return -1;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, position.x, position.y);
    }
}
