package dk.sdu.se4.cellular.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;

public class GameInputManager extends InputAdapter {
    private final GameData gameData;
    public GameInputManager(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            gameData.triggerPause();
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameData.setClickedPos(new Position(screenX, screenY));
        return true;
    }
}
