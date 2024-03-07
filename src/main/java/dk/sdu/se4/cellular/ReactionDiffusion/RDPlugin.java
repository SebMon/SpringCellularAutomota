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
        HashMap<Position, Cell> cells = new HashMap<>();
        for (int x = 0; x < gameData.getGameWidth(); x++) {
            for (int y = 0; y < gameData.getGameHeight(); y++) {
                Position position = new Position(x, y);
                cells.put(position, new RDCell(position, 1, 0));
            }
        }

        // Cross start
        /*
        for (int x = gameData.getGameWidth() / 2 - 2; x < gameData.getGameWidth() / 2 + 2; x++) {
            for (int y = 0; y < gameData.getGameHeight(); y++) {
                ((RDCell) cells.get(new Position(x, y))).setB(1);
            }
        }
        for (int y = gameData.getGameHeight() / 2 - 2; y < gameData.getGameHeight() / 2 + 2; y++) {
            for (int x = 0; x < gameData.getGameWidth(); x++) {
                ((RDCell) cells.get(new Position(x, y))).setB(1);
            }
        }

         */


        // Random square start
        Random random = new Random();
        int areaW = gameData.getGameWidth() / 2;
        int areaH = gameData.getGameHeight() / 2;

        int randomX = (int)((gameData.getGameWidth() - areaW) * random.nextFloat());
        int randomY = (int)((gameData.getGameHeight() - areaH) * random.nextFloat());

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

    @Override
    public String getSimName() {
        return "reaction_diffusion";
    }
}
