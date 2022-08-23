package dk.sdu.se4.cellular.common.data;

import java.util.Collection;
import java.util.HashMap;

public class World {
    private HashMap<Position, Cell> cells;

    public World() {
        cells = new HashMap<>();
    }

    public void setCells(HashMap<Position, Cell> cells) {
        this.cells = cells;
    }

    public boolean isPositionOccupied(Position pos) {
        return cells.containsKey(pos);
    }

    public Cell getCell(Position pos) {
        return cells.get(pos);
    }

    public void removeCell(Position pos) {
        cells.remove(pos);
    }

    public void putCell(Position pos, Cell cell) {
        cells.put(pos, cell);
    }

    public Collection<Cell> getCells() {
        return cells.values();
    }
}
