package nye.progtech.service.game;

import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.persistence.impl.json.JsonHandler;
import nye.progtech.persistence.impl.xml.XmlHandler;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.command.InputHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GamePlayTest {

    private GamePlay underTest;

    private Scanner mockScanner;
    private InputHandler mockInputHandler;

    private GameState mockGameState;

    @BeforeEach
    public void setup() {
        mockScanner = mock(Scanner.class);
        mockInputHandler = mock(InputHandler.class);
        mockGameState = mock(GameState.class);
        when(mockScanner.nextLine()).thenReturn("testUser");
        underTest = new GamePlay(mockScanner,mockInputHandler,mockGameState);
    }

    @Test
    void testGamePlayShouldGetTheUserAndCreateANewGameWithUserWithJsonRepositoryWhenCalled() {
        //Given
        when(mockScanner.nextLine()).thenReturn("testUser","1", "\n"); // Simulate user input
        //When
        underTest = new GamePlay(mockScanner);
        //Then
        verify(mockScanner, times(3)).nextLine(); // Verify that nextLine was called
        Assertions.assertEquals("testUser", underTest.getGameState().getUserName());
        Assertions.assertEquals(JsonHandler.class, underTest.getGameRepository().getClass());
    }

    @Test
    void testGamePlayShouldGetTheUserAndCreateANewGameWithUserWithXmlRepositoryWhenCalled() {
        //Given
        when(mockScanner.nextLine()).thenReturn("testUser","2", "\n"); // Simulate user input
        //When
        underTest = new GamePlay(mockScanner);
        //Then
        verify(mockScanner, times(3)).nextLine(); // Verify that nextLine was called
        Assertions.assertEquals("testUser", underTest.getGameState().getUserName());
        Assertions.assertEquals(XmlHandler.class, underTest.getGameRepository().getClass());
    }

    @Test
    void testGamePlayShouldGetTheUserAndCreateANewGameButFirstWithInvalidChoiceWhenCalled() {
        //Given
        when(mockScanner.nextLine()).thenReturn("testUser","InvalidGameRepository", "1", "\n"); // Simulate user input
        //When
        underTest = new GamePlay(mockScanner);
        //Then
        verify(mockScanner, times(4)).nextLine(); // Verify that nextLine was called
        Assertions.assertEquals("testUser", underTest.getGameState().getUserName());
        Assertions.assertEquals(JsonHandler.class, underTest.getGameRepository().getClass());
    }

    @Test
    void testInitGameShouldStartTheGameLoopWhenCalled() {
        //Given
        underTest = new GamePlay(mockScanner, mockInputHandler, mockGameState);
        // Setup mock
        when(mockScanner.nextLine()).thenReturn("6"); // Simulate user input
        when(mockGameState.isStillPlaying()).thenReturn(true,false); // Simulate game state changes
        when(mockInputHandler.handleInput(anyString())).thenReturn(mockGameState);

        //When
        underTest.InitGame();

        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called
        verify(mockInputHandler, times(1)).handleInput(any());
    }

    @Test
    void testSetGameStateShouldReturnTheGameStateThatWasSetWhenCalled() {
        //Given
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
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));

        //When
        underTest.setGameState(expected);
        GameState actual = underTest.getGameState();

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
    void testGetGameStateShouldReturnGameStateWhenCalled() {
        //Given

        //When
        GameState actual =underTest.getGameState();
        //Then
        assertNull(actual.getHero());
        assertNull(actual.getBoard());
        assertNull( actual.getUserName());

    }
}