package nye.progtech.persistence.impl.xml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class XmlDatabase {

    private final Map<Integer, String> gameStateMap = new HashMap<>();
    private int nextId = 1;

    @XmlTransient
    public int getNextId() {
        return nextId;
    }

    @XmlElement
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
