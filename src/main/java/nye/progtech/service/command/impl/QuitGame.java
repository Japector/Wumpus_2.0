package nye.progtech.service.command.impl;

import java.util.Objects;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QuitGame implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuitGame.class);

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("6", input);
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("Quit game was invoked to end the game play.");
        gameState.setStillPlaying(false);
        return gameState;
    }
}
