package dk.sdu.se4.cellular.ReactionDiffusion;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.Position;

public class RDCell extends Cell {
    private float a;
    private float b;

    public RDCell(Position position, float a, float b) {
        super(position, Color.BLACK);
        this.a = a;
        this.b = b;
    }

    public void updateColor() {
        super.setColor(new Color(b, b, b, 1));
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }
}
