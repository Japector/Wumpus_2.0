package nye.progtech.service.command.impl;


import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.jdbc.DatabaseService;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.command.InputHandler;
import nye.progtech.service.util.GameUtil;
import nye.progtech.ui.BoardRender;
import nye.progtech.ui.ClassicRender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class StartGameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private GameRepository mockGameRepository;
    private AutoCloseable closeable;
    private InputHandler mockInputHandler;
    private BoardRender mockBoardRender ;
    private Scanner mockScanner ;
    private GameState mockGameState;

    StartGame underTest;


    @BeforeEach
    public void setUpStreams() {
        mockGameRepository = mock(GameRepository.class);
        mockInputHandler = mock(InputHandler.class);
        mockBoardRender = mock(BoardRender.class);
        mockScanner = mock(Scanner.class);
        mockGameState = mock(GameState.class);
        underTest =  new StartGame(mockBoardRender, mockScanner, mockInputHandler, mockGameRepository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="5";
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
    public void testProcessShouldHandleUserInputWhenCalledWithValidInput() {


        // Setup mock
        when(mockScanner.nextLine()).thenReturn("w", "q"); // Simulate user input
        when(mockGameState.getBoard()).thenReturn(mock(Board.class));
        when(mockGameState.isGivenUpGame()).thenReturn(false,false, true); // Simulate game state changes
        when(mockInputHandler.handleInput(anyString())).thenReturn( mockGameState, mockGameState);

        //When
        GameState result = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(2)).nextLine(); // Verify that nextLine was called
        verify(mockInputHandler, times(2)).handleInput(any());
    }

    @Test
    public void testProcessShouldHandleUserInputWhenCalledWithInValidInput() {

        //Given
        List<String> expectedError = List.of("Invalid command, only the followings are allowed:",
        "-> a - left turn",
                "-> d - right turn",
                "-> w - move forward",
                "-> ' ' - shoot arrow",
                "-> s - take gold",
                "-> q - give up",
                "-> p - postpone game",
                "");
        String expected = expectedError.stream().collect(Collectors.joining("")) + "\r";


        // Setup mock
        when(mockScanner.nextLine()).thenReturn("t"); // Simulate user input
        when(mockGameState.getBoard()).thenReturn(mock(Board.class));
        when(mockGameState.isGivenUpGame()).thenReturn(false, true); // Simulate game state changes


        //Create with mock
        underTest  = new StartGame(mockBoardRender, mockScanner, mockInputHandler, mockGameRepository);

        //When
        GameState result = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called
        assertEquals(expected, outContent.toString().replace("\t","").replace("\n",""));
    }


    @Test
    public void testProcessShouldSaveGameWhenItIsWon() {
        //Given
                // Setup mock
        when(mockScanner.nextLine()).thenReturn("w","\n"); // Simulate user input
        when(mockGameState.getBoard()).thenReturn(mock(Board.class));
        when(mockGameState.isFinishedGame()).thenReturn(false, true);
        when(mockInputHandler.handleInput("w")).thenReturn(mockGameState);
        when(mockGameState.getNumberOfSteps()).thenReturn(10);


        //When
        GameState result = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(2)).nextLine(); // Verify that nextLine was called
       // assertEquals(expected, outContent.toString());
    }


    @Test
    public void testProcessShouldSaveGameWhenItIsPostponed() {
        //Given
        // Setup mock
        when(mockScanner.nextLine()).thenReturn("p","\n"); // Simulate user input
        when(mockGameState.getBoard()).thenReturn(mock(Board.class));
        when(mockGameState.isPostponeGame()).thenReturn(false, true);
        when(mockInputHandler.handleInput("p")).thenReturn(mockGameState);
        when(mockGameState.getNumberOfSteps()).thenReturn(10);

        //When
        GameState result = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called
        // assertEquals(expected, outContent.toString());
    }
}