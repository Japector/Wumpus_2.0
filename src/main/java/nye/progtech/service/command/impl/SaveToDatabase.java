package nye.progtech.service.command.impl;

import java.util.Objects;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SaveToDatabase implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveToDatabase.class);

    private final GameRepository gameRepository;

    public SaveToDatabase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("4", input);
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("User ({}) initiated to save the current game with the following state\n{}", gameState.getUserName(), gameState);
        gameRepository.saveGame(gameState);
        return gameState;
    }
}
