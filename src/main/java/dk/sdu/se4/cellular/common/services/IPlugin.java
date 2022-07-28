package dk.sdu.se4.cellular.common.services;

import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.World;

public interface IPlugin {
    void start(GameData gameData, World world);
    void stop(GameData gameData, World world);
}
