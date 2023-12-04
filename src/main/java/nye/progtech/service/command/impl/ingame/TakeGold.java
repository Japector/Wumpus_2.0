package nye.progtech.service.command.impl.ingame;

import java.util.Objects;

import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.service.command.Command;
import nye.progtech.service.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TakeGold implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TakeGold.class);

    private final MapUtil mapUtil;

    public TakeGold(MapUtil mapUtil) {
        this.mapUtil = mapUtil;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("s", input);
    }

    @Override
    public GameState process(GameState gameState) {
        Hero oldHero = gameState.getHero();
        int[] heroPosition = oldHero.getPosition();
        char[][] oldMap = gameState.getBoard().getMap();
        if (mapUtil.getChar(heroPosition, oldMap) == 'G') {
            LOGGER.info("Player ({}) takes the gold!", gameState.getUserName());
            gameState.setHero(new Hero(heroPosition, oldHero.getDirection(), oldHero.getNumberOfArrows(), true));
            gameState.setBoard(mapUtil.setPosition(heroPosition, '_', oldMap));
        }
        return gameState;
    }
}
