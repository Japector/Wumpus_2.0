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


class ClassicRenderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AutoCloseable closeable;
    ClassicRender underTest = new ClassicRender();

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

        List<String> expectedBoard = new ArrayList<>(
                List.of("\033[H\033[2JWWWWWW",
                        "WP  PW",
                        "WUGP W",
                        "W|   W",
                        "W^ P W",
                        "WWWWWW",
                        ""));
        String expected = expectedBoard.stream().collect(Collectors.joining("\r\n"));
        //When
        underTest.printBoard(new GameState(board, hero), arrow);
        //When--Then
        assertEquals(expected,outContent.toString());

    }

}