package nye.progtech.service.command.impl.createmap;

import java.util.List;

import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.impl.CreateMap;
import nye.progtech.service.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddElement implements Command {
    private static final String INPUT_PATTERN = "^ADD [WUGPH] [A-T] (1[0-9]|20|[2-9])$";
    private int[] position = new int[2];
    private char element;
    private final MapUtil mapUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMap.class);

    public AddElement(MapUtil mapUtil) {
        this.mapUtil = mapUtil;
    }

    @Override
    public boolean canProcess(String input) {
        if (input.matches(INPUT_PATTERN)) {
            String[] splitString = input.split(" ");
            position[0] = Integer.parseInt(splitString[3]) - 1;
            position[1] = splitString[2].charAt(0) - 'A';
            element = splitString[1].charAt(0);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public GameState process(GameState gameState) {
        if (List.of('W', 'U', 'G', 'P').contains(element)) {
            gameState.setBoard(mapUtil.setPosition(position, element, gameState.getBoard().getMap()));
            LOGGER.info("A(n) {} was added at position: {}", element, position);
        } else {
            Hero oldHero = gameState.getHero();
            Hero newHero = new Hero(position, oldHero.getDirection(), oldHero.getNumberOfArrows(), oldHero.getHasGold());
            LOGGER.info("Hero was moved to {}", position);
            gameState.setHero(newHero);
            gameState.setStartPosition(newHero.getPosition());
        }
        return gameState;
    }
}
