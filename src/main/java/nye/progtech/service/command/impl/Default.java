package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Default implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Default.class);

    @Override
    public boolean canProcess(String input) {
        return true;
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.warn("Input was fallen back to the default command.");
        System.out.println("This command is unknown!");
        return gameState;
    }
}
