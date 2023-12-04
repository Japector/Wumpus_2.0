package nye.progtech.service.util;

import nye.progtech.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class MapUtilTest {

    MapUtil underTest = new MapUtil();

    @Test
    void testSetPositionShouldChangeTheCharAtTheDefinedPositionOnTheMapWhenCalled() {
        //Given
        List<String> initialBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        char[][] map = new char[6][], expected = new char[6][];
        for (int i = 0; i < initialBoard.size(); i++) {
            map[i] = initialBoard.get(i).toCharArray();
        }
        int[] position = {1,1};
        char charachter = 'P';

        List<String> expectedBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "WP__PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        for (int i = 0; i < initialBoard.size(); i++) {
            expected[i] = expectedBoard.get(i).toCharArray();
        }
        //When
        Board actual = underTest.setPosition(position, charachter, map);
        //Then
        Assertions.assertArrayEquals(expected,actual.getMap());
    }

    @Test
    void testGetCharShouldReturnTheCharAtGivenPositionOnTheMap() {
        //Given
        List<String> initialBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        char[][] map = new char[6][];
        for (int i = 0; i < initialBoard.size(); i++) {
            map[i] = initialBoard.get(i).toCharArray();
        }
        int[] position = {2,1};
        char expected = 'U';
        //When
        char actual = underTest.getChar(position, map);
        //Then
        Assertions.assertEquals(expected, actual);
    }
}