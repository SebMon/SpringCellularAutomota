package dk.sdu.se4.cellular.common.data;

import com.badlogic.gdx.graphics.Color;

public abstract class Cell {
    private Position position;
    private Color color;

    public Cell(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Position getPos() {
        return position;
    }

    public void setPos(Position pos) {
        this.position = pos;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
