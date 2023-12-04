package nye.progtech.service.command.impl.createmap;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.impl.CreateMap;
import nye.progtech.service.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveElement implements Command {
    private static final String INPUT_PATTERN = "^REMOVE [WUGP] [A-T] (1[0-9]|20|[1-9])$";
    private final int[] position = new int[2];
    private final MapUtil mapUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMap.class);

    public RemoveElement(MapUtil mapUtil) {
        this.mapUtil = mapUtil;
    }

    @Override
    public boolean canProcess(String input) {
        if (input.matches(INPUT_PATTERN)) {
            String[] splitString = input.split(" ");
            position[0] = Integer.parseInt(splitString[3]) - 1;
            position[1] = splitString[2].charAt(0) - 'A';
            return true;
        } else {
            return false;
        }

    }

    @Override
    public GameState process(GameState gameState) {
        int[] newPosition = new int[2];
        newPosition = position;
        gameState.setBoard(mapUtil.setPosition(newPosition, '_', gameState.getBoard().getMap()));
        LOGGER.info("An element was removed from position: {}", position);
        return gameState;
    }
}
