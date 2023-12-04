package nye.progtech.service.command.impl.createmap;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.util.MapUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddElementTest {


    private AddElement underTest ;

    private MapUtil mockMapUtil;

    @BeforeEach
    public void setUpStreams() {
        mockMapUtil = mock(MapUtil.class);
        underTest =  new AddElement(mockMapUtil);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="ADD U B 2";
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
    void testProcessShouldCorrectlyAddElementToTheMapWhenCalled() {

        //Given

        List<String> gameStateInputStart = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "C 3 S 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "W_GP_W",
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
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInputEnd));
        when(mockMapUtil.setPosition(any(int[].class),any(char.class),any(char[][].class))).thenReturn(expected.getBoard());
        underTest.canProcess("ADD U B 3");

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


    @Test
    void testProcessShouldCorrectlyChangeThePositionOfTheHeroOnTheMapWhenCalled() {

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
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInputStart));
        underTest.canProcess("ADD H B 4");

        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(new int[] {3, 1}, actual.getHero().getPosition());
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