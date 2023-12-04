package nye.progtech.service.builder;

import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameStateBuilderTest {

    private GameStateBuilder underTest = new GameStateBuilder();

    @Test
    void testBuilderShouldGenerateTheCorrectGameStateWhenGivenAFlatInput() {
        //Given
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

        List<String> mapInput = gameStateInput.stream().map(String::new).collect(Collectors.toList());
        mapInput.subList(0,3).clear();
        char[][] map = new char[mapInput.size()][];
        for (int i = 0; i < mapInput.size(); i++) {
            map[i] = mapInput.get(i).toCharArray();
        }
        Board expectedBoard = new Board(mapInput.size(),map);
        Hero expectedHero = new HeroBuilder().builder(gameStateInput.get(1));

        //When
        GameState actual = underTest.builder(String.join("", gameStateInput));
        //Then


        Assertions.assertArrayEquals(expectedHero.getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expectedHero.getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expectedHero.getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expectedHero.getHasGold(), actual.getHero().getHasGold());

        Assertions.assertArrayEquals(expectedBoard.getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expectedBoard.getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(0,actual.getNumberOfSteps());
        Assertions.assertArrayEquals(new int[] {4,1},actual.getStartPosition());
        assertFalse(actual.isFinishedGame());

    }
}