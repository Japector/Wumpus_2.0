package nye.progtech.persistence;

import java.util.List;
import java.util.Map;

import nye.progtech.model.GameState;

public interface GameRepository {
    void saveGame(GameState gameState);

    GameState loadGame(String userName, int gameID);

    boolean checkGameExists(String userName, int gameID);

    Map<Integer, GameState> returnGames();
}
