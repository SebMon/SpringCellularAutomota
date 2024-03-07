package dk.sdu.se4.cellular.ReactionDiffusion;

import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IInputHandler;
import org.springframework.stereotype.Component;

@Component
public class RDInput implements IInputHandler {
    private World world;
    private GameData gameData;

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.gameData = gameData;
    }

    public void leftClick(Position pos) {
        RDCell clickedCell = (RDCell) world.getCell(pos);
        clickedCell.setB(1);
    }

    @Override
    public String getSimName() {
        return "reaction_diffusion";
    }
}
