package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.json.JsonHandler;
import nye.progtech.persistence.impl.json.JsonService;
import nye.progtech.persistence.impl.xml.XmlHandler;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.command.InputHandler;
import nye.progtech.ui.BoardRender;
import nye.progtech.ui.ClassicRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateMapTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private GameRepository mockGameRepository;
    private AutoCloseable closeable;
    private InputHandler mockInputHandler;
    private BoardRender mockBoardRender ;
    private Scanner mockScanner ;
    private GameState mockGameState;
    private CreateMap underTest;

    @BeforeEach
    public void setUpStreams() {
        mockGameRepository = mock(GameRepository.class);
        mockInputHandler = mock(InputHandler.class);
        mockBoardRender = mock(BoardRender.class);
        mockScanner = mock(Scanner.class);
        mockGameState = mock(GameState.class);
        underTest =  new CreateMap(mockScanner, mockBoardRender, mockInputHandler);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="1";
        boolean expected = true;
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCanProcessShouldReturnFalseWhenGetsTheInCorrectInput() {
        //Given
        String input ="m";
        boolean expected = false;
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testProcessShouldCorrectlyProcessTheGameWhenCalled() {

        //Given

        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 true ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        when(mockScanner.nextLine()).thenReturn("6", "ADD U B 3");
        when(mockInputHandler.handleInput("ADD U B 3")).thenReturn(expected);
        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());

        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(false,actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }

    @Test
    void testProcessShouldNotLetInvalidRowNumberToBeProcessedWhenCalled() {

        //Given

        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 true ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        when(mockScanner.nextLine()).thenReturn("6", "ADD U B 10","ADD U B 3");
        when(mockInputHandler.handleInput("ADD U B 3")).thenReturn(mockGameState, expected);
        when(mockGameState.isFinishedGame()).thenReturn(false);
        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());

        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(false,actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }

    @Test
    void testProcessShouldNotLetInvalidColumnToBeProcessedWhenCalled() {

        //Given

        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 true ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        when(mockScanner.nextLine()).thenReturn("6", "ADD U Z 3", "ADD U B 3");
        when(mockInputHandler.handleInput("ADD U B 3")).thenReturn(mockGameState, expected);
        when(mockGameState.isFinishedGame()).thenReturn(false);
        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());

        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(false,actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }

    @Test
    void testProcessShouldLetAddHeroToTheMapWhenCalled() {

        //Given

        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 true ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        when(mockScanner.nextLine()).thenReturn("6", "ADD H B 5", "ADD U B 3");
        when(mockInputHandler.handleInput(anyString())).thenReturn(mockGameState, expected);
        when(mockGameState.isFinishedGame()).thenReturn(false);
        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());

        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(false,actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }

    @Test
    void testProcessShouldNotProcessIfTheBoardSizeIsInvalidWhenCalled() {

        //Given

        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 true ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        when(mockScanner.nextLine()).thenReturn("32","6", "ADD U B 3");
        when(mockInputHandler.handleInput("ADD U B 3")).thenReturn( expected);
        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());

        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(false,actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }
}