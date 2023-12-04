package nye.progtech.persistence.impl.json;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.xml.XmlDatabase;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class JsonDatabaseTest {


    private final JsonDatabase underTest = new JsonDatabase();
    private GameState expected;
    private String gameStateFlatMap;
    private GameRepository mockGameRepository;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 4 N 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        gameStateFlatMap = String.join("", gameStateInput);
        expected = new GameStateBuilder().builder(gameStateFlatMap);
        mockScanner = mock(Scanner.class);
        mockGameRepository = mock(GameRepository.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetGameStateMapShouldReturnTheCorrectHashMapWhenCalled() {
        //Given
        underTest.addGameState(gameStateFlatMap);
        //When
        Map<Integer, String> actual = underTest.getGameStateMap();
        //Then
        assertEquals(Set.of(1), actual.keySet());
        assertEquals(gameStateFlatMap, actual.get(1));
    }

    @Test
    void testAddGameStateShouldAddTheGameStateToTheMapWhenCalled() {
        //Given
        //When
        underTest.addGameState(gameStateFlatMap);
        //Then
        assertEquals(Set.of(1), underTest.getGameStateMap().keySet());
        assertEquals(gameStateFlatMap, underTest.getGameStateMap().get(1));
    }

    @Test
    void testGetGameStateShouldReturnTheGameStateWhenCalled() {
        //Given
        underTest.addGameState(gameStateFlatMap);
        //When
        String actual = underTest.getGameState(1);
        //Then
        assertEquals(gameStateFlatMap, actual);
    }

    @Test
    void testUpdateNextIdShouldSetTheNextIdToTheMaxKeyValuePlusOneWhenCalled() {
        //Given
        underTest.addGameState(gameStateFlatMap);
        underTest.updateNextId();
        //When
        int actual = underTest.getNextId();
        //Then
        assertEquals(2, actual);
    }

    @Test
    void testUpdateNextIdShouldKeepTheNextIdToOneWhenTheGameStateMapIsEmpty() {
        //Given
        underTest.updateNextId();
        //When
        int actual = underTest.getNextId();
        //Then
        Assertions.assertEquals(1, actual);
    }
}