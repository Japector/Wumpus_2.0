package nye.progtech.persistence.impl.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import nye.progtech.exceptions.CouldNotSaveGame;
import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.builder.GameStateBuilder;


public class XmlHandler implements GameRepository {

    private final XmlServiceInterface xmlService;


    public XmlHandler(XmlServiceInterface xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public void saveGame(GameState gameState)  {
            xmlService.getDatabase().addGameState(gameState.toString());
        try {
            xmlService.saveDatabase();
        } catch (Exception e) {
            System.out.println("Game state could not be added to the repository");
            throw new CouldNotSaveGame("Could not save game: " + gameState, e);
        }
    }

    @Override
    public GameState loadGame(String userName, int gameID) {
        String gameStateFlat = xmlService.getDatabase().getGameState(gameID);
        GameState gameState = new GameStateBuilder().builder(gameStateFlat);
        if (Objects.equals(gameState.getUserName(), userName) && gameState.getNumberOfSteps() > -1 && !gameState.isFinishedGame()) {
            System.out.println("Game successfully loaded!");
            return gameState;
        } else {
            System.out.println("Game cannot be loaded as it is either finished or failed.");
            return null;
        }
    }

    @Override
    public boolean checkGameExists(String userName, int gameID) {
        String gameStateFlat = xmlService.getDatabase().getGameState(gameID);
        if (gameStateFlat == null) {
            return false;
        } else {
            GameState gameState = new GameStateBuilder().builder(gameStateFlat);
            return Objects.equals(gameState.getUserName(), userName);
        }
    }

    @Override
    public Map<Integer, GameState> returnGames() {
        Map<Integer, String> gameStateMap = xmlService.getDatabase().getGameStateMap();
        Map<Integer, GameState> gameStateList = new HashMap<>();
        for (Map.Entry<Integer, String> entry : gameStateMap.entrySet()) {
            gameStateList.put(entry.getKey(), new GameStateBuilder().builder(entry.getValue()));
        }
        return gameStateList;
    }

}
