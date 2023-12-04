package nye.progtech.service.command.impl;

import java.util.Objects;
import java.util.Scanner;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseService;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.command.Command;
import nye.progtech.service.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadFromDatabase implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadFromDatabase.class);
    private final GameRepository gameRepository;
    private final Scanner scanner;

    public LoadFromDatabase(Scanner scanner, GameRepository gameRepository) {
        this.scanner = scanner;
        this.gameRepository = gameRepository;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("3", input);
    }


    @Override
    public GameState process(GameState gameState) {

        GameUtil.printAvailableGames(gameState.getUserName(), gameRepository.returnGames());
        System.out.println("Please enter the gameid you want to load:");
        String id = scanner.nextLine();
        LOGGER.info("Load map command was invoked with the following game ID: {}", id);
        GameState loadedGame = null;
        if (id.matches("^[0-9]+$")) {
            int idConverted = Integer.parseInt(id);
            if (gameRepository.checkGameExists(gameState.getUserName(), idConverted)) {
                loadedGame = gameRepository.loadGame(gameState.getUserName(), idConverted);
            }
        } else {
            System.out.println("Invalid game ID!");
        }
        return loadedGame != null ? loadedGame : gameState;
    }

}
