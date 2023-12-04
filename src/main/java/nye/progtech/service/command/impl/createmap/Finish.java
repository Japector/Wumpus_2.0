package nye.progtech.service.command.impl.createmap;

import java.util.ArrayList;
import java.util.List;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.impl.CreateMap;
import nye.progtech.service.validator.impl.ValidatorImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Finish implements Command {
    private final ValidatorImplementation validatorImplementation = new ValidatorImplementation();
    private static final String INPUT_PATTERN = "^FINISH$";
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMap.class);

    @Override
    public boolean canProcess(String input) {
        return input.matches(INPUT_PATTERN);
    }

    @Override
    public GameState process(GameState gameState) {
        int mapSize = gameState.getBoard().getSize();

        String[] heroLine = new String[4];
        heroLine[0] = String.valueOf(mapSize);
        heroLine[1] = String.valueOf((char) (gameState.getHero().getPosition()[1] + 'A'));
        heroLine[2] = String.valueOf(gameState.getHero().getPosition()[0] + 1);
        heroLine[3] = String.valueOf(gameState.getHero().getDirection());

        List<String> listStream = new ArrayList<>();
        listStream.add(String.join(" ", heroLine));
        char[][] charMap = gameState.getBoard().getMap();
        for (int i = 0; i < mapSize; i++) {
            listStream.add(new String(charMap[i]));
        }

        if (validatorImplementation.fileInputValidator(listStream)) {
            gameState.setFinishedGame(true);
            LOGGER.info("The table passed all the validation tests!");
        } else {
            System.out.println("The map is not yet finished!");
        }
        return gameState;
    }
}
