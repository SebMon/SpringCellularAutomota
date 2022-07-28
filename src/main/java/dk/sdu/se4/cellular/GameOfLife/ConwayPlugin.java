package dk.sdu.se4.cellular.GameOfLife;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IPlugin;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ConwayPlugin implements IPlugin {
    private final Color color = Color.WHITE;
    @Override
    public void start(GameData gameData, World world) {
        Random random = new Random();
        for (int x = 0; x < gameData.getWindowWidth(); x++) {
            for (int y = 0; y < gameData.getWindowHeight(); y++) {
                if (random.nextBoolean()) {
                    world.addCell(new ConwayCell(new Position(x, y), color));
                }
            }
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Cell cell : world.getCells()) {
            if (cell instanceof ConwayCell) {
                world.removeCell(cell.getPos());
            }
        }
    }
}
