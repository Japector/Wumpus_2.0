package nye.progtech.service.command.impl.createmap;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.command.InputHandler;
import nye.progtech.service.command.impl.StartGame;
import nye.progtech.service.util.MapUtil;
import nye.progtech.ui.BoardRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

class RemoveElementTest {


    private RemoveElement underTest ;

    private MapUtil mockMapUtil;

    @BeforeEach
    public void setUpStreams() {
        mockMapUtil = mock(MapUtil.class);
        underTest =  new RemoveElement(mockMapUtil);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="REMOVE U B 2";
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
    void testProcessShouldCorrectlyRemoveElementFromTheMapWhenCalled() {

        //Given

        List<String> gameStateInputStart = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "C 3 S 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState startGameState = new GameStateBuilder().builder(String.join("", gameStateInputStart));

        List<String> gameStateInputEnd = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "C 3 S 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "W_GP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInputEnd));
        when(mockMapUtil.setPosition(any(int[].class),any(char.class),any(char[][].class))).thenReturn(expected.getBoard());
        underTest.canProcess("REMOVE U B 3");

        //When
        GameState actual = underTest.process(startGameState);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals( expected.getHero().getDirection(), actual.getHero().getDirection());
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