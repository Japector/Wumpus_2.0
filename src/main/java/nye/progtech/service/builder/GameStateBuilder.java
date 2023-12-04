package nye.progtech.service.builder;

import java.util.Arrays;

import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GameStateBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateBuilder.class);

    public GameStateBuilder() {}

    public GameState builder(String gameState) {
        LOGGER.info("Game state builder creating a map using flatmap:\n{}", gameState);
        String[] inputString = gameState.split(" ");

        String heroLine = String.join(" ", Arrays.copyOfRange(inputString, 5, 10));
        Hero hero = new HeroBuilder().builder(heroLine);

        String flatMap = inputString[11];
        int size = Integer.parseInt(inputString[10]);
        Board board = new BoardBuilder().builder(flatMap, size);

        String userName = inputString[0];
        int numberOfSteps = Integer.parseInt(inputString[1]);
        int[] startingPosition = new int[2];
        startingPosition[0] = Integer.parseInt(inputString[3]) - 1;
        startingPosition[1] = inputString[2].charAt(0) - 'A';
        boolean finished = inputString[4].equals("true");

        return new GameState(userName, board, hero, numberOfSteps, startingPosition, finished);
    }

}
