package dk.sdu.se4.cellular.ReactionDiffusion;

import dk.sdu.se4.cellular.common.data.Cell;
import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;
import dk.sdu.se4.cellular.common.services.IProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RDProcessor implements IProcessor {
    float rateA = 1f;
    float rateB = 0.5f;
    float feedRate = 0.055f;
    float killRate = 0.062f;

    @Override
    public void process(GameData gameData, World world) {
        HashMap<Position, Cell> newCells = new HashMap<>();
        for (int x = 0; x < gameData.getGameWidth(); x++) {
            for (int y = 0; y < gameData.getGameHeight(); y++) {
                Position pos = new Position(x, y);
                RDCell cell = (RDCell) world.getCell(pos);
                RDCell newCell = new RDCell(cell.getPos(), cell.getA(), cell.getB());

                newCell.setA(evaluateA(newCell, world));
                newCell.setB(evaluateB(newCell, world));
                newCell.updateColor();
                newCells.put(newCell.getPos(), newCell);
            }
        }
        world.setCells(newCells);

    }

    private float evaluateA(RDCell cell, World world) {
        //return cell.getA() + (rateA * diffuseA(cell, world) - cell.getA() * cell.getB() * cell.getB() + feedRate * (1 - cell.getA())) * gameData.getDelta();
        return cell.getA() + (rateA * diffuseA(cell, world) - cell.getA() * cell.getB() * cell.getB() + feedRate * (1 - cell.getA()));
    }

    private float evaluateB(RDCell cell, World world) {
        //return cell.getB() + (rateB * diffuseB(cell, world) + cell.getA() * cell.getB() * cell.getB() - (killRate + feedRate) * cell.getB()) * gameData.getDelta();
        return cell.getB() + (rateB * diffuseB(cell, world) + cell.getA() * cell.getB() * cell.getB() - (killRate + feedRate) * cell.getB());
    }

    private float diffuseA(RDCell cell, World world) {
        Position pos = cell.getPos();
        float a = cell.getA();
        float am = 0;

        RDCell tempCell;

        // top left
        tempCell = (RDCell) world.getCell(new Position(pos.x - 1, pos.y - 1));
        if (tempCell != null) am += tempCell.getA() * 0.05f;

        // top
        tempCell = (RDCell) world.getCell(new Position(pos.x , pos.y - 1));
        if (tempCell != null) am += tempCell.getA() * 0.2f;

        // top right
        tempCell = (RDCell) world.getCell(new Position(pos.x + 1, pos.y - 1));
        if (tempCell != null) am += tempCell.getA() * 0.05f;

        // left
        tempCell = (RDCell) world.getCell(new Position(pos.x - 1, pos.y));
        if (tempCell != null) am += tempCell.getA() * 0.2f;

        // self
        am -= a;

        // right
        tempCell = (RDCell) world.getCell(new Position(pos.x + 1, pos.y));
        if (tempCell != null) am += tempCell.getA() * 0.2f;

        // bottom left
        tempCell = (RDCell) world.getCell(new Position(pos.x - 1, pos.y + 1));
        if (tempCell != null) am += tempCell.getA() * 0.05f;

        // bottom
        tempCell = (RDCell) world.getCell(new Position(pos.x, pos.y + 1));
        if (tempCell != null) am += tempCell.getA() * 0.2f;

        // bottom right
        tempCell = (RDCell) world.getCell(new Position(pos.x + 1, pos.y + 1));
        if (tempCell != null) am += tempCell.getA() * 0.05f;

        return am;
    }

    private float diffuseB(RDCell cell, World world) {
        Position pos = cell.getPos();
        float b = cell.getB();
        float bm = 0;

        RDCell tempCell;

        // top left
        tempCell = (RDCell) world.getCell(new Position(pos.x - 1, pos.y - 1));
        if (tempCell != null) bm += tempCell.getB() * 0.05f;

        // top
        tempCell = (RDCell) world.getCell(new Position(pos.x , pos.y - 1));
        if (tempCell != null) bm += tempCell.getB() * 0.2f;

        // top right
        tempCell = (RDCell) world.getCell(new Position(pos.x + 1, pos.y - 1));
        if (tempCell != null) bm += tempCell.getB() * 0.05f;

        // left
        tempCell = (RDCell) world.getCell(new Position(pos.x - 1, pos.y));
        if (tempCell != null) bm += tempCell.getB() * 0.2f;

        // self
        bm -= b;

        // right
        tempCell = (RDCell) world.getCell(new Position(pos.x + 1, pos.y));
        if (tempCell != null) bm += tempCell.getB() * 0.2f;

        // bottom left
        tempCell = (RDCell) world.getCell(new Position(pos.x - 1, pos.y + 1));
        if (tempCell != null) bm += tempCell.getB() * 0.05f;

        // bottom
        tempCell = (RDCell) world.getCell(new Position(pos.x, pos.y + 1));
        if (tempCell != null) bm += tempCell.getB() * 0.2f;

        // bottom right
        tempCell = (RDCell) world.getCell(new Position(pos.x + 1, pos.y + 1));
        if (tempCell != null) bm += tempCell.getB() * 0.05f;

        return bm;
    }

    @Override
    public String getSimName() {
        return "reaction_diffusion";
    }
}
