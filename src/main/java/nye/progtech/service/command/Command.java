package nye.progtech.service.command;

import nye.progtech.model.GameState;

public interface Command {
    boolean canProcess(String input);

    GameState process(GameState gameState);

}
