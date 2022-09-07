package dk.sdu.se4.cellular.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IInputHandler;
import dk.sdu.se4.cellular.common.services.IPlugin;
import dk.sdu.se4.cellular.common.services.IProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class Game implements ApplicationListener {
    private SpriteBatch batch;
    private Texture texture;
    private GameData gameData;
    private World world;
    private final ArrayList<IProcessor> processors = new ArrayList<>();
    private final ArrayList<IPlugin> plugins = new ArrayList<>();
    private IInputHandler inputHandler;
    private int hFactor = 1;
    private int wFactor = 1;
    private final float frameLength = 0.01f;
    private BitmapFont font;
    private int fps;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameData = new GameData();

        // Solve this - should not be hard set
        gameData.setWindowHeight(400);
        gameData.setWindowWidth(400);
        gameData.setGameHeight(100);
        gameData.setGameWidth(100);

        hFactor = gameData.getWindowHeight() / gameData.getGameHeight();
        wFactor = gameData.getWindowWidth() / gameData.getGameWidth();

        world = new World();

        for (IPlugin plugin : plugins) {
            plugin.start(gameData, world);
        }
        inputHandler.start(gameData, world);
        Gdx.input.setInputProcessor(new GameInputManager(gameData));
        fps = 0;

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        handleInputOfCells();

        // Update delta
        gameData.setDelta(gameData.getDelta() + Gdx.graphics.getDeltaTime());

        // Update world if unpaused and enough time has passed since last update
        if (!gameData.isPaused() && gameData.getDelta() >= frameLength) {

            for (IProcessor processor : processors) {
                processor.process(gameData, world);
            }
            fps = (int) (1 / gameData.getDelta());
            gameData.setDelta(0);
        }



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Pixmap pmap = new Pixmap(gameData.getWindowWidth(), gameData.getWindowHeight(), Pixmap.Format.RGBA8888);

        // Draw each cell to pixmap, use factors to draw cells depending on game size
        for (Cell cell : world.getCells()) {
            pmap.setColor(cell.getColor());
            pmap.fillRectangle(cell.getPos().x * wFactor, cell.getPos().y * hFactor, wFactor, hFactor);
        }

        // Draw pixmap to screen
        texture = new Texture(pmap);
        pmap.dispose();
        Sprite sprite = new Sprite(texture);
        batch.begin();
        sprite.setPosition(0, 0);
        sprite.draw(batch);

        // Write fps
        font.draw(batch, Integer.toString(fps), 5, gameData.getWindowHeight() - 5);

        // Dispose
        batch.end();
        texture.dispose();


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
        font.dispose();
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

    @Autowired
    public void addInputHandler(IInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void handleInputOfCells() {
        Position clickedPos = gameData.getClickedPos();
        gameData.setClickedPos(null);
        if (clickedPos != null) {
            int x = (clickedPos.x - (clickedPos.x % wFactor)) / wFactor;
            int y = (clickedPos.y - (clickedPos.y % hFactor)) / hFactor;
            inputHandler.leftClick(new Position(x, y));
        }
    }
}
