package com.mygdx.game.Camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Player.Player;

public class Camera
{
    public int speed;
    public int worldWidth;
    public int worldHeight;
    public int cameraWidth;
    public int cameraHeight;
    public OrthographicCamera mainCamera;

    public Camera(int width, int height, int worldWidth, int worldHeight)
    {
        speed = 4;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.cameraWidth = width;
        this.cameraHeight = height;
        mainCamera = new OrthographicCamera(cameraWidth, cameraHeight);
    }

    public void render(SpriteBatch batch)
    {
        mainCamera.update();
        batch.setProjectionMatrix(mainCamera.combined);
    }

    public void positionUpdate(Player player)
    {
        mainCamera.position.set(getPositionX(player), getPositionY(player), 0);
    }

    public int getPositionX(Player player)
    {
        if(player.position.x > cameraWidth / 2 && player.position.x < worldWidth - cameraWidth / 2)
            return player.position.x;
        else if(player.position.x < worldWidth / 2)
            return cameraWidth / 2;
        else
            return worldWidth - cameraWidth / 2;
    }

    public int getPositionY(Player player)
    {
        if(player.position.y > cameraHeight / 2 && player.position.y < worldHeight - cameraHeight / 2)
            return player.position.y;
        else if(player.position.y < worldHeight / 2)
            return cameraHeight / 2;
        else
            return worldHeight - cameraHeight / 2;
    }
}

