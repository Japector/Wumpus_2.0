package nye.progtech.service.command.impl.createmap;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.command.impl.ReadMapFromFile;
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class FinishTest {


    private Finish underTest = new Finish();
    @Mock
    private GameUtil gameUtil;


    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="FINISH";
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
    void testProcessShouldSetTheFinishedFlagToTrueWhenCalledWithAValidMap() {
        //Given


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

        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertTrue(actual.isFinishedGame());

    }
    @Test
    void testProcessShouldNotSetTheFinishedFlagToTrueWhenCalledWithAValidMap() {
        //Given


        List<String> gameStateExpected = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "W_GP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateExpected));

        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertFalse(actual.isFinishedGame());

    }
}