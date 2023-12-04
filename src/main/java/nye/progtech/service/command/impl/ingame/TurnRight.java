package nye.progtech.service.command.impl.ingame;

import java.util.Objects;

import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.service.command.Command;
import nye.progtech.service.util.HeroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TurnRight implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnRight.class);
    private final HeroUtil heroUtil;

    public TurnRight(HeroUtil heroUtil) {
        this.heroUtil = heroUtil;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("d", input);
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("Player ({}) wants to take a right turn", gameState.getUserName());
        char newDirection = heroUtil.rotateHero('d', gameState.getHero().getDirection());
        Hero oldHero = gameState.getHero();
        gameState.setHero(new Hero(oldHero.getPosition(), newDirection, oldHero.getNumberOfArrows(), oldHero.getHasGold()));
        return gameState;
    }
}
