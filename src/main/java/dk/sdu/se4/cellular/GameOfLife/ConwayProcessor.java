package dk.sdu.se4.cellular.GameOfLife;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConwayProcessor implements IProcessor {
    private final Color color = Color.WHITE;
    @Override
    public void process(GameData gameData, World world) {
        for (int x = 0; x < gameData.getWindowWidth(); x++) {
            for (int y = 0; y < gameData.getWindowHeight(); y++) {
                Position pos = new Position(x, y);
                Cell cell = world.getCell(pos);
                short neighbours = aliveNeighbours(pos, world);
                if (cell == null) {
                    if (neighbours == 3) {
                        world.addCell(new ConwayCell(pos, color));
                    }
                }
                else {
                    if (!(neighbours == 2 || neighbours == 3)) {
                        world.removeCell(pos);
                    }
                }
            }
        }
    }

    // Check if rules are correct
    private short aliveNeighbours(Position pos, World world) {
        short neighbours = 0;
        // Top left
        if (!world.isPositionEmpty(new Position(pos.x - 1, pos.y + 1))) {
            neighbours += 1;
        }

        // Top
        if (!world.isPositionEmpty(new Position(pos.x, pos.y + 1))) {
            neighbours += 1;
        }

        // Top right
        if (!world.isPositionEmpty(new Position(pos.x + 1, pos.y - 1))) {
            neighbours += 1;
        }

        // Left
        if (!world.isPositionEmpty(new Position(pos.x - 1, pos.y))) {
            neighbours += 1;
        }

        // Right
        if (!world.isPositionEmpty(new Position(pos.x + 1, pos.y ))) {
            neighbours += 1;
        }

        // Bottom left
        if (!world.isPositionEmpty(new Position(pos.x - 1, pos.y - 1))) {
            neighbours += 1;
        }

        // Bottom
        if (!world.isPositionEmpty(new Position(pos.x, pos.y - 1))) {
            neighbours += 1;
        }

        // Bottom right
        if (!world.isPositionEmpty(new Position(pos.x + 1, pos.y - 1))) {
            neighbours += 1;
        }

        return neighbours;
    }
}
