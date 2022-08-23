package dk.sdu.se4.cellular.GameOfLife;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IInputHandler;
import org.springframework.stereotype.Component;

@Component
public class ConwayInput implements IInputHandler {
    private World world;
    private GameData gameData;
    private final Color color = Color.WHITE;

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.gameData = gameData;
    }

    @Override
    public void leftClick(Position pos) {
        Cell clickedCell = world.getCell(pos);
        if (clickedCell == null) {
            world.putCell(pos, new ConwayCell(pos, color));
        }
        else {
            world.removeCell(pos);
        }
    }
}
