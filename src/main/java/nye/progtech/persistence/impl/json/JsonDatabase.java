package nye.progtech.persistence.impl.json;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonDatabase {
    private final Map<Integer, String> gameStateMap = new HashMap<>();
    private int nextId = 1;

    public int getNextId() {
        return nextId;
    }

    public Map<Integer, String> getGameStateMap() {
        return gameStateMap;
    }

    public void addGameState(String gameState) {
        gameStateMap.put(nextId++, gameState);
    }

    public String getGameState(int id) {
        return gameStateMap.get(id);
    }

    public void updateNextId() {
        if (!gameStateMap.isEmpty()) {
            nextId = Collections.max(gameStateMap.keySet()) + 1;
        }
    }
}
