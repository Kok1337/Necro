package com.mygdx.game.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Camera.Camera;
import com.mygdx.game.Control.MainSystem;
import com.mygdx.game.Model.JustObject;
import com.mygdx.game.Model.Passage;
import com.mygdx.game.Model.Thing;
import com.mygdx.game.Player.Player;
import com.mygdx.game.Position;
import com.mygdx.game.SearchPath.AStar;
import com.mygdx.game.SearchPath.Node;

import java.util.ArrayList;

public abstract class World implements Screen
{

    public Camera camera;
    public Game game;
    public MainSystem system;
    public World(Game game, int worldWidth, int worldHeight, int nodeSize, Position position)
    {
        this.game = game;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.startPlayerPosition = position;
        this.nodeSize = nodeSize;
    }

    public AStar aStar;
    public Player player;
    public SpriteBatch batch;
    public Position startPlayerPosition;
    public int worldWidth;
    public int worldHeight;
    public int nodeSize;

    public ArrayList<Node> closeAllTime;
    public ArrayList<Passage> passages;
    public ArrayList<Thing> things;

    @Override
    public void show()
    {
        batch = new SpriteBatch();
        player = new Player(startPlayerPosition.x, startPlayerPosition.y, nodeSize);
        aStar = new AStar(worldWidth, worldHeight, nodeSize);

        closeAllTime = new ArrayList<Node>();
        passages = new ArrayList<Passage>();
        things = new ArrayList<Thing>();

        getCloseNode();
        getPassage();
        getThing();
        aStar.setCloseAllTime(closeAllTime);
        camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), worldWidth, worldHeight);
        system = new MainSystem(this);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        camera.render(batch);
        system.update();
        aStar.render(batch);
        player.render(batch);

        for (Passage passage : passages)
        {
            passage.render(batch);
        }

        for (Thing thing : things)
        {
            thing.render(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {
        JustObject.counter = 0;
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

    abstract void getCloseNode();
    abstract void getPassage();
    abstract void getThing();
}
