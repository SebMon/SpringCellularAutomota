package dk.sdu.se4.cellular.common.services;

import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.Position;
import dk.sdu.se4.cellular.common.data.World;

public interface IInputHandler {
    void start(GameData gameData, World world);
    void leftClick(Position pos);
}
