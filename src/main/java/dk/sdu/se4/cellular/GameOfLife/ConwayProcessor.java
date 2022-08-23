package dk.sdu.se4.cellular.GameOfLife;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

//@Component
public class ConwayProcessor implements IProcessor {
    private final Color color = Color.WHITE;
    @Override
    public void process(GameData gameData, World world) {
        HashMap<Position, Cell> newCells = new HashMap<>();
        for (int x = 0; x < gameData.getGameWidth(); x++) {
            for (int y = 0; y < gameData.getGameHeight(); y++) {
                Position pos = new Position(x, y);
                Cell cell = world.getCell(pos);
                short neighbours = aliveNeighbours(pos, world);
                if (cell == null) {
                    if (neighbours == 3) {
                        newCells.put(pos, new ConwayCell(pos, color));
                    }
                }
                else {
                    if (neighbours == 2 || neighbours == 3) {
                        newCells.put(pos, new ConwayCell(pos, color));
                    }
                }
            }
        }
        world.setCells(newCells);
    }

    // Check if rules are correct
    // This map goes from top left to bottom right
    private short aliveNeighbours(Position pos, World world) {
        short neighbours = 0;
        // Top left
        if (world.isPositionOccupied(new Position(pos.x - 1, pos.y - 1))) {
            neighbours += 1;
        }
        // Top
        if (world.isPositionOccupied(new Position(pos.x, pos.y - 1))) {
            neighbours += 1;
        }
        // Top right
        if (world.isPositionOccupied(new Position(pos.x + 1, pos.y - 1))) {
            neighbours += 1;
        }

        // Left
        if (world.isPositionOccupied(new Position(pos.x - 1, pos.y))) {
            neighbours += 1;
        }

        // Right
        if (world.isPositionOccupied(new Position(pos.x + 1, pos.y))) {
            neighbours += 1;
        }

        // Bottom left
        if (world.isPositionOccupied(new Position(pos.x - 1, pos.y + 1))) {
            neighbours += 1;
        }

        // Bottom
        if (world.isPositionOccupied(new Position(pos.x, pos.y + 1))) {
            neighbours += 1;
        }

        // Bottom right
        if (world.isPositionOccupied(new Position(pos.x + 1, pos.y + 1))) {
            neighbours += 1;
        }

        return neighbours;
    }
}
