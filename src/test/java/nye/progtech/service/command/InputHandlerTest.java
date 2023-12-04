package nye.progtech.service.command;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.mock;

class InputHandlerTest {

    GameRepository mockgameRepository;
    Scanner mockScanner;
    InputHandler underTest;

    @Test
    void testHandleInputShouldInvokeTheCorrectCommandWhenCalledWithAValidUserInput() {
        //Given
        mockgameRepository = mock(GameRepository.class);
        mockScanner = mock(Scanner.class);
        underTest = new InputHandler(mockgameRepository,mockScanner);
        String input = "6";
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        underTest.setGameState(expected);

        //When
        GameState actual = underTest.handleInput(input);

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
    void testHandleInputShouldReturnWhenThereIsNoInputWhenCalled() {
        //Given
        mockgameRepository = mock(GameRepository.class);
        mockScanner = mock(Scanner.class);
        underTest = new InputHandler(mockgameRepository,mockScanner);
        String input = "";
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        underTest.setGameState(expected);

        //When
        GameState actual = underTest.handleInput(input);

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
    void testHandleInputShouldNotProcessAnyCommandWhenInvokedWithAnIncorrectUserInput() {
        //Given
        mockgameRepository = mock(GameRepository.class);
        mockScanner = mock(Scanner.class);
        underTest = new InputHandler(mockgameRepository,mockScanner);
        String input = "NotValidInput";
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 E 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));
        underTest.setGameState(expected);

        //When
        GameState actual = underTest.handleInput(input);

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


}