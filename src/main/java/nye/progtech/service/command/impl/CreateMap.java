package nye.progtech.service.command.impl;


import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.InputHandler;
import nye.progtech.ui.BoardRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreateMap implements Command {

    private final Scanner scanner;
    private final BoardRender boardRender;
    private final InputHandler inputHandler;

    public CreateMap(Scanner scanner, BoardRender boardRender, InputHandler inputHandler) {
        this.scanner = scanner;
        this.boardRender = boardRender;
        this.inputHandler = inputHandler;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMap.class);

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("1", input);
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("Create map command was invoked");
        boardRender.clearConsole();
        Integer mapSize = null;
        do {
        System.out.println("Please give me the size of the map you want to create (5<N<21)");
        String size = scanner.nextLine();
        if (size.matches("^([6-9]|1[0-9]|20)$")) {
            mapSize = Integer.parseInt(size);
        }
        } while (mapSize == null);


        char[][] charMap = new char[mapSize][];
        for (int i = 0; i < mapSize; i++) {
            if (i == 0 || i + 1 == mapSize) {
                charMap[i] = "W".repeat(mapSize).toCharArray();
            } else {
                charMap[i] = ("W" + "_".repeat(mapSize - 2) + "W").toCharArray();
            }
        }

        Board board = new Board(mapSize, charMap);
        Hero hero = new Hero(new int[] {1, 1}, 'E', 1 + (mapSize - 3) / 6, false);
        GameState gameStateConstructed = new GameState(board, hero);
        gameStateConstructed.setUserName(gameState.getUserName());
        LOGGER.info("Simple table ({}x{}) was created by user: {}", mapSize, mapSize, gameState.getUserName());
        while (!gameStateConstructed.isFinishedGame()) {
            boardRender.printBoard(gameStateConstructed, null);
            inputHandler.setGameState(gameStateConstructed);
            String choice = scanner.nextLine();
            String pattern = "(FINISH|ADD [WUGPH] [B-T] (1[0-9]|20|[2-9])|REMOVE [WUGPH] [B-T] (1[0-9]|" +
                    "20|[2-9])|DIRECTION [NESW])";
            if (Pattern.matches(pattern, choice) && validRowAndColumn(choice, mapSize)) {
                LOGGER.info("Input handler was invoked with command: {}", choice);
                gameStateConstructed = inputHandler.handleInput(choice);
            } else {
                System.out.println("Invalid command, only the followings are allowed:\n\t-> add [WUGPH] [B-T] (1[0-9]|20|[2-9])" +
                        "\n\t-> remove [WUGPH] [B-T] (1[0-9]|20|[2-9])\n\t-> direction [NESW]\n\t-> finish");
            }
        }
        gameStateConstructed.setFinishedGame(false);
        return gameStateConstructed;
    }

    boolean validRowAndColumn(String choice, int mapSize) {
        boolean result = true;
        String[] choiceList = choice.split(" ");
        if (List.of("ADD", "REMOVE").contains(choiceList[0])) {
            if (mapSize + 'A' - 1 - choiceList[2].charAt(0)  <= 0 || mapSize - Integer.parseInt(choiceList[3]) <= 0) {
                result = false;
            }
        }
        return result;
    }

}
