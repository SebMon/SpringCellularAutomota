package dk.sdu.se4.cellular.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class World {
    private final HashMap<Position, Cell> cells;

    public World() {
        cells = new HashMap<>();
    }

    public boolean isPositionEmpty(Position pos) {
        return !cells.containsKey(pos);
    }

    public Cell getCell(Position pos) {
        return cells.get(pos);
    }

    public void removeCell(Position pos) {
        cells.remove(pos);
    }

    public void addCell(Cell cell) {
        cells.put(cell.getPos(), cell);
    }

    public Collection<Cell> getCells() {
        return cells.values();
    }
}
