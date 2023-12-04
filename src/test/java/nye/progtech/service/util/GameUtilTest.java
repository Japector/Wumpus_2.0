package nye.progtech.service.util;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testClearConsoleShouldClearTheConsoleWhenInvoked() {
        GameUtil.clearConsole();
        assertEquals("\033[H\033[2J", outContent.toString());
    }

    //isGameCompleted
    @Test
    void testIsGameCompletedShouldReturnFalseWhenTheGameIsNotCompleted() {
        //Given
        int[] startPosition = {2, 2}, currentPosition = {2, 4};
        boolean hasGold = true, expected =false;
        //When
        boolean actual = GameUtil.isGameCompleted(startPosition, currentPosition, hasGold);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testIsGameCompletedShouldReturnFalseWhenTheHeroIsWithoutGold() {
        //Given
        int[] startPosition = {2, 2}, currentPosition = {2, 2};
        boolean hasGold = false, expected =false;
        //When
        boolean actual = GameUtil.isGameCompleted(startPosition, currentPosition, hasGold);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testIsGameCompletedShouldReturnTureWhenTheGameIsCompleted() {
        //Given
        int[] startPosition = {2, 2}, currentPosition = {2, 2};
        boolean hasGold = true, expected =true;
        //When
        boolean actual = GameUtil.isGameCompleted(startPosition, currentPosition, hasGold);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testScoreBoardShouldPrintAScoreBoardWhenCalled() {
        //Given
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 10 B 5 true ",
                        "B 5 E 1 true ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        String gameStateFlatMap = String.join("", gameStateInput);
        GameState winnerGame = new GameStateBuilder().builder(gameStateFlatMap);
        Map<Integer, GameState> gameStateList = new HashMap<>();
        gameStateList.put(1,winnerGame);

        String title = "Scoreboard";
        int tableWidth = 50;
        int padding = (tableWidth - title.length()) / 2;
        String paddedTitle = String.format("%" + padding + "s%s%" + padding + "s", "", title, "");
        StringBuilder sb = new StringBuilder();
        String horizontalLine = "+----------------------+---------------------------+";
        String headerFormat = "| %-20s | %-25s |\n";

        sb.append(paddedTitle).append("\r\n");
        sb.append(horizontalLine).append("\r\n");
        sb.append(String.format(headerFormat, "Player name", "Number of finished games"));
        sb.append(horizontalLine).append("\r\n");


        String rowFormat = "| %-20s | %25d |\n";
        sb.append(String.format(rowFormat, "testUser", 1));


        sb.append(horizontalLine).append("\r\n");
        sb.append("\n\nPress enter to continue...").append("\r\n");
        //When
        GameUtil.scoreBoard(gameStateList);
        //Then
        assertEquals("\033[H\033[2J" + sb.toString(), outContent.toString());
    }


    @Test
    void testPrintAvailableGamesShouldPrintABoardWhenCalled()  {
        //Given
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 10 B 5 false ",
                        "C 5 E 1 true ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        String gameStateFlatMap = String.join("", gameStateInput);
        GameState winnerGame = new GameStateBuilder().builder(gameStateFlatMap);
        Map<Integer, GameState> gameStateList = new HashMap<>();
        gameStateList.put(1,winnerGame);

        String title = "Games available for load";
        int tableWidth = 120; // Adjusted table width to accommodate more columns
        int padding = (tableWidth - title.length()) / 2;
        String paddedTitle = String.format("%" + padding + "s%s%" + padding + "s", "", title, "");
        StringBuilder sb = new StringBuilder();
        String horizontalLine = "+" + "-".repeat(tableWidth - 2) + "+";
        String headerFormat = "| %-10s | %-20s | %-20s | %-10s | %-20s | %-21s |\n";

        sb.append(paddedTitle).append("\r\n");
        sb.append(horizontalLine).append("\r\n");
        sb.append(String.format(headerFormat, "Game ID", "Player name", "Number of steps", "Gold", "Number of arrows", "Board size"));
        sb.append(horizontalLine).append("\r\n");


        String rowFormat = "| %-10d | %-20s | %-20d | %-10b | %-20d | %-21d |\n";
        sb.append(String.format(rowFormat, 1, "testUser", 10, true, 1, 6));


        sb.append(horizontalLine).append("\r\n");
        //When
        GameUtil.printAvailableGames("testUser", gameStateList);
        //Then
        assertEquals("\033[H\033[2J" + sb.toString(), outContent.toString());
    }

}