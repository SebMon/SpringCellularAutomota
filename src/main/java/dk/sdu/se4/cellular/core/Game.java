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
import java.util.List;

@Component
public class Game implements ApplicationListener {
    private SpriteBatch batch;
    private Texture texture;
    private GameData gameData;
    private World world;
    private List<IProcessor> processors;
    private IProcessor processor;
    private List<IPlugin> plugins;
    private IPlugin plugin;
    private List<IInputHandler> inputHandlers;
    private IInputHandler inputHandler;
    private int hFactor = 1;
    private int wFactor = 1;
    private BitmapFont font;
    private int fps;

    private int gameWidth = 100;
    private int gameHeight = 100;
    private int windowWidth = 400;
    private int windowHeight = 400;
    private String simName = "reaction_diffusion";

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameData = new GameData();

        gameData.setWindowHeight(windowHeight);
        gameData.setWindowWidth(windowWidth);
        gameData.setGameHeight(gameHeight);
        gameData.setGameWidth(gameWidth);

        hFactor = gameData.getWindowHeight() / gameData.getGameHeight();
        wFactor = gameData.getWindowWidth() / gameData.getGameWidth();

        world = new World();

        for (IPlugin plugin : plugins) {
            if (plugin.getSimName().equalsIgnoreCase(this.simName)) {
                this.plugin = plugin;
            }
        }
        for (IInputHandler inputHandler : inputHandlers) {
            if (inputHandler.getSimName().equalsIgnoreCase(this.simName)) {
                this.inputHandler = inputHandler;
            }
        }
        for (IProcessor processor : processors) {
            if (processor.getSimName().equalsIgnoreCase(this.simName)) {
                this.processor = processor;
            }
        }
        if (processor == null || inputHandler == null || plugin == null) {
            throw new RuntimeException("Not a valid simulation name. Use 'game_of_life' or 'reaction_diffusion'");
        }

        this.plugin.start(gameData, world);
        this.inputHandler.start(gameData, world);

        Gdx.input.setInputProcessor(new GameInputManager(gameData));
        fps = 0;
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        if(gameData.isPaused()) {
            handleInputOfCells();
        }

        // Update delta
        gameData.setDelta(gameData.getDelta() + Gdx.graphics.getDeltaTime());

        // Update world if unpaused and enough time has passed since last update
        float frameLength = 0.01f;
        if (!gameData.isPaused() && gameData.getDelta() >= frameLength) {

            processor.process(gameData, world);
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
    public void addProcessors(List<IProcessor> processors) {
        this.processors = processors;
    }

    @Autowired
    public void addPlugin(List<IPlugin> plugins) {
        this.plugins = plugins;
    }

    @Autowired
    public void addInputHandler(List<IInputHandler> inputhandlers) {
        this.inputHandlers = inputhandlers;
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

    public int getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public String getSimName() {
        return simName;
    }

    public void setSimName(String simName) {
        this.simName = simName;
    }
}
