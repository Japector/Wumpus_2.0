package nye.progtech.service.command.impl.ingame;

import nye.progtech.model.Arrow;
import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.util.HeroUtil;
import nye.progtech.service.util.MapUtil;
import nye.progtech.ui.ClassicRender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ShootArrowTest {

    ShootArrow underTest = new ShootArrow(new MapUtil(), new HeroUtil(), new ClassicRender(), new Arrow(new int[] {0, 0}, 'N', 1));

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input =" ";
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
        MapUtil mapUtil = new MapUtil();
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 N 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));

        //When
        GameState actual = underTest.process(expected);
        expected.setBoard(mapUtil.setPosition(new int[] {2, 1},'_', expected.getBoard().getMap()));
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
    void testProcessShouldNotLetTheHeroToShootAnArrowWhenTheHeroDoesNotHaveAnyArrows() {

        //Given
        MapUtil mapUtil = new MapUtil();
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 5 N 0 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));

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
        Assertions.assertEquals(expected.isFinishedGame(),actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());
    }
}