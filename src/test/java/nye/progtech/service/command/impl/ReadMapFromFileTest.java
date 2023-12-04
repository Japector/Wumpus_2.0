package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.command.InputHandler;
import nye.progtech.service.input.InputProcessing;
import nye.progtech.service.util.GameUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ReadMapFromFileTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Scanner mockScanner;
    private InputProcessing mockInputProcessing;
    private ReadMapFromFile underTest;
    @Mock
    private GameUtil gameUtil;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        mockScanner = mock(Scanner.class);
        mockInputProcessing = mock(InputProcessing.class);
        underTest = new ReadMapFromFile(mockScanner,mockInputProcessing);
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="2";
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
    void testProcessShouldReturnWithAValidGameStateWhenCalledWithAValidInput(@TempDir Path tempDir) {
        //Given

        Stream<String> gameStateInput = new ArrayList<>(
                                                List.of("6 B 5 E",
                                                        "WWWWWW",
                                                        "W___PW",
                                                        "WUGP_W",
                                                        "W____W",
                                                        "W__P_W",
                                                        "WWWWWW")).stream();
        String testPath = "wumpusz.txt";
        //File tempFile = tempDir.resolve(testPath).toFile();
        List<String> gameStateExpected = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateExpected));
        GameState mockGameState = mock(GameState.class);
        when(mockScanner.nextLine()).thenReturn(testPath);
        when(mockInputProcessing.fileInput(new File(testPath))).thenReturn(gameStateInput);
        //When
        GameState actual = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called
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
    void testProcessShouldNotValidateInvalidInputWhenCalled(@TempDir Path tempDir) throws Exception {
        //Given

        Stream<String> gameStateInput = new ArrayList<>(
                List.of("6 B 5 E",
                        "WWZZZW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW")).stream();
        String testPath = "wumpusz.txt";

        GameState mockGameState = mock(GameState.class);
        when(mockScanner.nextLine()).thenReturn(testPath);
        when(mockInputProcessing.fileInput(new File(testPath))).thenReturn(gameStateInput);
        //When
        GameState actual = underTest.process(mockGameState);
        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called
        Assertions.assertNull(actual.getBoard());
        Assertions.assertNull(actual.getHero());
    }
}