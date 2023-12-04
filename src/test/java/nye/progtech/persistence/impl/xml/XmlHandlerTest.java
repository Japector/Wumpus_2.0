package nye.progtech.persistence.impl.xml;

import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XmlHandlerTest {

    private XmlHandler underTest;

    private static final File XML_FILE = new File("GameStateDatabase.xml");
    private GameState expected;
    private String gameStateFlatMap;
    private XmlServiceInterface mockXmlService;
    private XmlDatabase mockXmlDatabase;

    @BeforeEach
    public void setup() throws IOException {
        if (!XML_FILE.exists()) {
            String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<xmlDatabase>\n" +
                    "    <gameStateMap>\n" +
                    "        <entry>\n" +
                    "            <key>1</key>\n" +
                    "            <value>user1 0 C 12 false C 12 E 3 false 15 WWWWWWWWWWWWWWWW____W________WW____W___U____WW____W________WW____P________WW_____W___G___WW__U__W_W_____WW__________P__WW_______W_____WW___W___U_____WW___W_________WW___WWWWWW____WW_____P__W____WW_P______W____WWWWWWWWWWWWWWWW</value>\n" +
                    "        </entry>\n" +
                    "    </gameStateMap>\n" +
                    "</xmlDatabase>";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(XML_FILE))) {
                writer.write(xmlContent);
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
        mockXmlService = mock(XmlServiceInterface.class);
        mockXmlDatabase = mock(XmlDatabase.class);
        underTest = new XmlHandler(mockXmlService);
    }

    @Test
    void testSaveGameShouldAddGameToRepositoryWhenCalled() {
        // Given
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        // When
        underTest.saveGame(expected);
        // Then
        Mockito.verify(mockXmlService.getDatabase(),times(1)).addGameState(expected.toString());
    }

    /*
    @Test
    void saveGameThrow() throws Exception {
        // Given
        doThrow(new RuntimeException("Database Error")).when(mockXmlService).saveDatabase();
        //When
        assertThrows(RuntimeException.class, () -> { underTest.saveGame(expected); });

    }
    */
    @Test
    void testSaveGameShouldThrowExceptionWhenItCantSaveTheGame() throws Exception {
        // Given
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        doThrow(new RuntimeException()).when(mockXmlService).saveDatabase();

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
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
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
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
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
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
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
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        GameState actual = underTest.loadGame("testUser",1);
        //Then
        Assertions.assertNull(actual);
    }


    @Test
    void testCheckGameExistsShouldReturnTrueWhenTheGameExists() {
        // Given
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
        // When
        boolean actual = underTest.checkGameExists("testUser",1);
        //Then
        assertTrue(actual);
    }

    @Test
    void testCheckGameExistsShouldReturnFalseWhenTheGameDoesNotExist() {
        // Given
        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameState(1)).thenReturn(gameStateFlatMap);
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

        when(mockXmlService.getDatabase()).thenReturn(mockXmlDatabase);
        when(mockXmlDatabase.getGameStateMap()).thenReturn(gamesToReturn);
        // When
        Map<Integer,GameState> actual = underTest.returnGames();
        //Then
        Assertions.assertEquals(expected.toString(),actual.get(1).toString());
    }
}