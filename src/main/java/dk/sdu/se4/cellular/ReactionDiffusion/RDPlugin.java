package dk.sdu.se4.cellular.ReactionDiffusion;

import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IPlugin;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

@Component
public class RDPlugin implements IPlugin {
    @Override
    public void start(GameData gameData, World world) {
        Random random = new Random();
        HashMap<Position, Cell> cells = new HashMap<>();
        for (int x = 0; x < gameData.getGameWidth(); x++) {
            for (int y = 0; y < gameData.getGameHeight(); y++) {
                Position position = new Position(x, y);
                cells.put(position, new RDCell(position, 1, 0));
            }
        }


        int randomX = (int)(gameData.getGameWidth() * random.nextFloat());
        int randomY = (int)(gameData.getGameHeight() * random.nextFloat());
        int areaW = gameData.getGameWidth() / 5;
        int areaH = gameData.getGameHeight() / 5;

        for (int x = randomX; x < (randomX + areaW) && x < gameData.getGameWidth(); x++) {
            for (int y = randomY; y < (randomY + areaH) && y < gameData.getGameHeight(); y++) {
                ((RDCell) cells.get(new Position(x, y))).setB(1);
            }
        }


        world.setCells(cells);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Cell cell : world.getCells()) {
            if (cell instanceof RDCell) {
                world.removeCell(cell.getPos());
            }
        }
    }
}
