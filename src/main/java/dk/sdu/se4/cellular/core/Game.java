package dk.sdu.se4.cellular.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IPlugin;
import dk.sdu.se4.cellular.common.services.IProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.ArrayList;

@Component
public class Game implements ApplicationListener {
    private SpriteBatch batch;
    private Texture texture;
    private GameData gameData;
    private World world;

    // Add plugin and processor here
    private final ArrayList<IProcessor> processors = new ArrayList<>();
    private final ArrayList<IPlugin> plugins = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameData = new GameData();

        // Solve this - should not be hard set
        gameData.setWindowHeight(400);
        gameData.setWindowWidth(400);

        world = new World();

        for (IPlugin plugin : plugins) {
            plugin.start(gameData, world);
        }

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        for (IProcessor processor : processors) {
            processor.process(gameData, world);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Pixmap pmap = new Pixmap(gameData.getWindowWidth(), gameData.getWindowHeight(), Pixmap.Format.RGBA8888);

        for (Cell cell : world.getCells()) {
            pmap.setColor(cell.getColor());
            pmap.drawPixel(cell.getPos().x, cell.getPos().y);
        }

        Texture texture = new Texture(pmap);
        pmap.dispose();
        Sprite sprite = new Sprite(texture);
        batch.begin();
        sprite.setPosition(0, 0);
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        if (texture != null) {
            texture.dispose();
        }
    }

    @Autowired
    public void addProcessor(IProcessor processor) {
        this.processors.add(processor);
    }

    @Autowired
    public void addPlugin(IPlugin plugin) {
        this.plugins.add(plugin);
    }
}