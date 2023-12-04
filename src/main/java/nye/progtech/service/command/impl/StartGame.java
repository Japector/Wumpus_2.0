package nye.progtech.service.command.impl;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.InputHandler;
import nye.progtech.ui.BoardRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartGame implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartGame.class);

    private final BoardRender boardRender;
    private final  Scanner scanner;
    private final InputHandler inputHandler;
    private final GameRepository gameRepository;

    public StartGame(BoardRender boardRender, Scanner scanner, InputHandler inputHandler,
                     GameRepository gameRepository) {
        this.boardRender = boardRender;
        this.scanner = scanner;
        this.inputHandler = inputHandler;
        this.gameRepository = gameRepository;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("5", input);
    }

    @Override
    public GameState process(GameState gameState) {
        gameState.setPostponeGame(false);
        while (!gameState.isGivenUpGame() && !gameState.isFinishedGame() && !gameState.isPostponeGame() && gameState.getBoard() != null) {
            boardRender.printBoard(gameState, null);
            inputHandler.setGameState(gameState);
            String choice = scanner.nextLine();
            if (Pattern.matches("[asdwqp ]", choice)) {
                LOGGER.info("Input handler was invoked with command: {}", choice);
                gameState = inputHandler.handleInput(choice);
            } else {
                System.out.println("Invalid command, only the followings are allowed:\n\t-> a - left turn" +
                        "\n\t-> d - right turn\n\t-> w - move forward\n\t-> ' ' - shoot arrow\n\t-> s - take gold\n\t-> q - give up" +
                        "\n\t-> p - postpone game");
            }

            if (gameState.isFinishedGame() || gameState.isPostponeGame()) {
                gameRepository.saveGame(gameState);
                if (gameState.isFinishedGame()) {
                    boardRender.clearConsole();
                    System.out.println("You have successfully finished the game!!!\nNumber of steps: " + gameState.getNumberOfSteps() +
                            "\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }
        }
        return gameState;
    }
}
