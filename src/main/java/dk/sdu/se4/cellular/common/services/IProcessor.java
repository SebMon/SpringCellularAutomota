package dk.sdu.se4.cellular.common.services;

import dk.sdu.se4.cellular.common.data.GameData;
import dk.sdu.se4.cellular.common.data.World;

public interface IProcessor {
    void process(GameData gameData, World world);
}
