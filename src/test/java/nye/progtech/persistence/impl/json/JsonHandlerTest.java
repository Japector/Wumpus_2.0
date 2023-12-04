package nye.progtech.persistence.impl.json;

import nye.progtech.exceptions.CouldNotSaveGame;
import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.xml.XmlDatabase;
import nye.progtech.persistence.impl.xml.XmlServiceInterface;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class JsonHandlerTest {

    private JsonHandler underTest;

    private static final File JSON_FILE = new File("GameStateDatabase.json");
    private GameState expected;
    private String gameStateFlatMap;
    private JsonServiceInterface mockJsonService;
    private JsonDatabase mockJsonDatabase;

    @BeforeEach
    public void setup() throws IOException {
        if (!JSON_FILE.exists()) {
            String jsonContent = "{\n" +
                    "\"gameStateMap\" : {\n" +
                    "\t\"1\" : \"user1 0 C 12 false C 12 E 3 false 15 WWWWWWWWWWWWWWWW____W________WW____W___U____WW____W________WW____P________WW_____W___G___WW__U__W_W_____WW__________P__WW_______W_____WW___W___U_____WW___W_________WW___WWWWWW____WW_____P__W____WW_P______W____WWWWWWWWWWWWWWWW\"\n" +
                    "},\n\"nextId\" : 2}";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE))) {
                writer.write(jsonContent);
            }
        }

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
        mockJsonService = mock(JsonServiceInterface.class);
        mockJsonDatabase = mock(JsonDatabase.class);
        underTest = new JsonHandler(mockJsonService);
    }

    @Test
    void testSaveGameShouldAddGameToRepositoryWhenCalled() {
        // Given
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        // When
        underTest.saveGame(expected);
        // Then
        Mockito.verify(mockJsonService.getDatabase(),times(1)).addGameState(expected.toString());
    }


    @Test
    void testSaveGameShouldThrowExceptionWhenItCantSaveTheGame() throws Exception {
        // Given
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        doThrow(new RuntimeException()).when(mockJsonService).saveDatabase();

        // When
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            underTest.saveGame(expected);
        });
        // Then
        assertTrue(thrown.getMessage().contains("Could not save game:"));
    }

    @Test
    void testLoadGameShouldReturnTheGameWhenCalledByTheUserWhoSavedIt() {
        // Given
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        GameState actual = underTest.loadGame("testUser",1);
        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());


        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(expected.isFinishedGame(),actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }

    @Test
    void testLoadGameShouldReturnNullWhenCalledByTheWrongUser() {
        // Given
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        GameState actual = underTest.loadGame("testUser1",1);
        //Then
        Assertions.assertNull(actual);
    }

    @Test
    void testLoadGameShouldReturnNullWhenTheGameIsAlreadyFinished() {
        // Given
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 true ",
                        "B 4 N 1 true ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        String gameStateFlatMap = String.join("", gameStateInput);
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        GameState actual = underTest.loadGame("testUser",1);
        //Then
        Assertions.assertNull(actual);
    }

    @Test
    void testLoadGameShouldReturnNullWhenTheGameIsFailed() {
        // Given
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser -1 B 5 true ",
                        "B 4 N 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        String gameStateFlatMap = String.join("", gameStateInput);
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        GameState actual = underTest.loadGame("testUser",1);
        //Then
        Assertions.assertNull(actual);
    }

    @Test
    void testCheckGameExistsShouldReturnTrueWhenTheGameExists() {
        // Given
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        boolean actual = underTest.checkGameExists("testUser",1);
        //Then
        assertTrue(actual);
    }

    @Test
    void testCheckGameExistsShouldReturnFalseWhenTheGameDoesNotExist() {
        // Given
        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        boolean actual = underTest.checkGameExists("testUser",4);
        //Then
        assertFalse(actual);
    }

    @Test
    void testReturnGamesShouldReturnEveryGameInTheRepositoryWhenCalled() {
        // Given
        Map<Integer,String> gamesToReturn = new HashMap<>();
        gamesToReturn.put(1,gameStateFlatMap);

        when(mockJsonService.getDatabase()).thenReturn(mockJsonDatabase);
        when(mockJsonDatabase.getGameStateMap()).thenReturn(gamesToReturn);
        // When
        Map<Integer,GameState> actual = underTest.returnGames();
        //Then
        Assertions.assertEquals(expected.toString(),actual.get(1).toString());
    }
}