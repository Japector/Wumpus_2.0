package nye.progtech.service.game;


import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import nye.progtech.model.GameState;
import nye.progtech.model.MenuStructure;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseService;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.persistence.impl.json.JsonHandler;
import nye.progtech.persistence.impl.json.JsonService;
import nye.progtech.persistence.impl.xml.XmlHandler;
import nye.progtech.persistence.impl.xml.XmlService;
import nye.progtech.service.command.InputHandler;
import nye.progtech.service.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GamePlay {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamePlay.class);
    private final Scanner scanner;
    private final InputHandler inputHandler;
    private GameState gameState;
    private GameRepository gameRepository = null;


    public GamePlay(Scanner scanner, InputHandler inputHandler, GameState gameState) {
        this.scanner = scanner;
        this.inputHandler = inputHandler;
        this.gameState = gameState;
    }

    public GamePlay(Scanner scanner) {
        this.scanner = scanner;
        System.out.println("\n\n\tThe Wumpus is waiting for you\n---------------------------------------\n\n\n" +
                "Please enter your user name:");
        String userName = scanner.nextLine();
        LOGGER.info("Read User Name = '{}'", userName);
        gameState = new GameState(userName);
        System.out.println("Please choose Game Repository\n\t-> 1 - Json\n\t-> 2 - XML\n\t-> 3 - JDBC");
        String c = scanner.nextLine();
        do {
            if (Objects.equals("1", c)) {
                gameRepository = new JsonHandler(JsonService.getInstance());
            } else if (Objects.equals("2", c)) {
                gameRepository = new XmlHandler(XmlService.getInstance());
            } else if (Objects.equals("3", c)) {
                gameRepository = new DatabaseHandler(DatabaseService.getInstance());
            } else {
                System.out.println("Invalid choice.");
                c = scanner.nextLine();
            }
        } while (gameRepository == null);
        inputHandler = new InputHandler(gameRepository, scanner);
        GameUtil.scoreBoard(gameRepository.returnGames());
        scanner.nextLine();
    }

    public void InitGame()  {
        LOGGER.info("Starting the Game.");
        while (gameState.isStillPlaying()) {
            GameUtil.clearConsole();
            inputHandler.setGameState(this.getGameState());
            MenuStructure.MENU_LIST.forEach(System.out::println);
            String choice = this.scanner.nextLine();
            LOGGER.info("User({}) tried to invoke the following menu point = '{}'", gameState.getUserName(), choice);
            if (Pattern.matches("[0-6]", choice)) {
                LOGGER.info("Input handler was called with '{}'", choice);
                this.setGameState(inputHandler.handleInput(choice));
            } else {
                LOGGER.warn("User chose incorrect menu point {}", choice);
                System.out.println("Invalid command, only 1-6 is accepted!");
            }
        }
        scanner.close();
        LOGGER.info("Game play is finished.");
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }
}
