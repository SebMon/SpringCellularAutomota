package dk.sdu.se4.cellular.common.data;

public class GameData {
    private int windowWidth;
    private int windowHeight;
    private int gameWidth;
    private boolean paused = false;
    private Position clickedPos;
    private float delta = 0;



    private int gameHeight;

    public GameData() {}

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

    public void triggerPause() {
        paused = !paused;
    }

    public void setClickedPos(Position pos) {
        clickedPos = pos;
    }

    public Position getClickedPos() {
        return clickedPos;
    }

    public boolean isPaused() {
        return paused;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }


}
