package nye.progtech.service.command.impl.ingame;

import java.util.Objects;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class PostponeGame implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiveUpGame.class);


    @Override
    public boolean canProcess(String input) {
        return Objects.equals("p", input);
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("Player {} has postponed the game", gameState.getUserName());
        gameState.setPostponeGame(true);
        return gameState;
    }
}
