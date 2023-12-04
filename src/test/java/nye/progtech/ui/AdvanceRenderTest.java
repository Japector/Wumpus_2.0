package nye.progtech.ui;

import nye.progtech.model.Arrow;
import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class AdvanceRenderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AutoCloseable closeable;
    AdvanceRender underTest = new AdvanceRender();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GRAY = "\u001B[47m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    @Mock
    private Board board;

    @Mock
    private Hero hero;

    @Mock
    private Arrow arrow;


    @BeforeEach
    public void setUpStreams() {
        closeable= MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testClearConsoleShouldClearTheConsoleWhenInvoked() {
        underTest.clearConsole();
        assertEquals("\033[H\033[2J", outContent.toString());
    }

    @Test
    void testPrintBoardShouldCorrectlyPrintTheBoardBasedOnElementsWhenCalled() {
        //Given
        char[][] map = new char[6][];
        List<String> inputBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "WP__PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        for (int i = 0; i < inputBoard.size(); i++) {
            map[i] = inputBoard.get(i).toCharArray();
        }
        given(arrow.getPosition()).willReturn(new int[] {3,1});
        given(arrow.getDirection()).willReturn('N');
        given(board.getMap()).willReturn(map);
        given(hero.getPosition()).willReturn(new int[] {4,1});
        given(hero.getDirection()).willReturn('N');

        String wallChar = ANSI_GRAY + "█" + ANSI_RESET;
        String pitChar = ANSI_BLUE + "P" + ANSI_RESET + " ";
        String wumpusChar = ANSI_RED+ "W" + ANSI_RESET + " ";
        String goldChar = ANSI_YELLOW+ "G" + ANSI_RESET + " ";
        String heroChar = ANSI_GREEN+ "↑" + ANSI_RESET + " ";
        String arrowChar = ANSI_GREEN+ "^" + ANSI_RESET + " ";
        String wallRow = wallChar.repeat(11);
        String padding = "  ";
        List<String> expectedBoard = new ArrayList<>(
                List.of("\033[H\033[2J 1  "+ wallRow + padding.repeat(4) + " Board Legend:",
                        " 2  " + wallChar + " " + pitChar + padding.repeat(2) + pitChar + wallChar + padding.repeat(4) + " █=Wall, W=Wumpus, G=Gold, P=Pit, ↑↓→←=Hero",
                        " 3  " + wallChar + " " + wumpusChar + goldChar + pitChar + padding + wallChar + padding.repeat(4) + " X=Start, <^>v=Arrow",
                        " 4  " + wallChar + " " + arrowChar + padding.repeat(3) +  wallChar + padding.repeat(4) + " Control:",
                        " 5  " + wallChar + " " + heroChar + padding + pitChar + padding + wallChar + padding.repeat(4) + " w=Move, a=Left turn, d=Right turn, s=Take gold",
                        " 6  " + wallRow + padding.repeat(4) + " ' '=Shoot Arrow, q=Give up, p=Postpone",
                        "    A B C D E F ",
                        "",
                        "Statistics:",
                        "Number of steps:\t0",
                        "Arrows left:\t\t0",
                        "Gold acquired:\tfalse",
                        ""));
        String expected = expectedBoard.stream().collect(Collectors.joining(""));
        //When
        underTest.printBoard(new GameState(board, hero), arrow);
        //When--Then
        assertEquals(expected,outContent.toString().replace("\r\n","").replace("\n",""));

    }

}