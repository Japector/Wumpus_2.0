package nye.progtech.service.command.impl.ingame;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.command.Command;
import nye.progtech.service.util.GameUtil;
import nye.progtech.service.util.HeroUtil;
import nye.progtech.service.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MoveForward implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveForward.class);

    private final MapUtil mapUtil;
    private final HeroUtil heroUtil;

    public MoveForward(MapUtil mapUtil, HeroUtil heroUtil) {
        this.mapUtil = mapUtil;
        this.heroUtil = heroUtil;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("w", input);
    }

    @Override
    public GameState process(GameState gameState) {
        Hero oldHero = gameState.getHero();
        int[] heroPosition = oldHero.getPosition();
        char[][] oldMap = gameState.getBoard().getMap();
        int[] newPosition = heroUtil.moveForward(heroPosition, oldHero.getDirection());
        char newPositionChar = mapUtil.getChar(newPosition, oldMap);
        int stepsTaken = gameState.getNumberOfSteps();
        int numberOfArrows = oldHero.getNumberOfArrows();

        if (Set.of('G', 'U', 'P', '_').contains(newPositionChar)) {
            stepsTaken++;
            LOGGER.info("Player ({}) decided to take a step forward from position: [{},{}]", gameState.getUserName(),
                    gameState.getHero().getPosition()[0], gameState.getHero().getPosition()[1]);
            gameState.setNumberOfSteps(stepsTaken);
            if (newPositionChar == 'U') {
                LOGGER.info("Player ({}) met with its faith (wumpus) and died: ", gameState.getUserName());
                gameState.setNumberOfSteps(-1);
                gameState.setGivenUpGame(true);
            } else if (newPositionChar == 'P') {
                LOGGER.info("Player ({}) lost an arrow in a pit: ", gameState.getUserName());
                numberOfArrows = numberOfArrows + (numberOfArrows == 0 ? 0 : -1);
            }
            LOGGER.info("Player ({}) successfully took the step to the new position: [{},{}] ", gameState.getUserName(),
                     newPosition[0], newPosition[1]);
            gameState.setHero(new Hero(newPosition, oldHero.getDirection(), numberOfArrows, oldHero.getHasGold()));
            boolean finished = GameUtil.isGameCompleted(gameState.getStartPosition(), newPosition, oldHero.getHasGold());
            gameState.setFinishedGame(finished);
        }
        return gameState;
    }
}
