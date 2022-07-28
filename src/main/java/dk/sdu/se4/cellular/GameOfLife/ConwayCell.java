package dk.sdu.se4.cellular.GameOfLife;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.Position;
import org.springframework.stereotype.Component;

public class ConwayCell extends Cell {
    public ConwayCell(Position position, Color color) {
        super(position, color);
    }
}
