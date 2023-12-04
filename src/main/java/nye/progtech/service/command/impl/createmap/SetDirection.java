package nye.progtech.service.command.impl.createmap;

import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.impl.CreateMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetDirection implements Command {
    private static final String INPUT_PATTERN = "^DIRECTION [NESW]$";

    private char direction;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMap.class);

    @Override
    public boolean canProcess(String input) {
        if (input.split(" ").length > 1) {
            direction = input.split(" ")[1].charAt(0);
        }
        return input.matches(INPUT_PATTERN);
    }

    @Override
    public GameState process(GameState gameState) {
        Hero oldHero = gameState.getHero();
        gameState.setHero(new Hero(oldHero.getPosition(), direction, oldHero.getNumberOfArrows(), oldHero.getHasGold()));
        LOGGER.info("Hero's direction was set to: {}", direction);
        return gameState;
    }
}
