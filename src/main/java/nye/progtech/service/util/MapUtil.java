package nye.progtech.service.util;

import java.util.Arrays;

import nye.progtech.model.Board;


public class MapUtil {

    public Board setPosition(int[] position, char character, char[][] map) {
        int mapSize = map.length;
        char[][] newMap =  new char[mapSize][];

        for (int i = 0; i < mapSize; i++) {
            newMap[i] = Arrays.copyOf(map[i], map[i].length);
        }

        newMap[position[0]][position[1]] = character;
        return new Board(mapSize, newMap);
    }

    public char getChar(int[] position, char[][] map) {
        char foundChar;
        foundChar = map[position[0]][position[1]];
        return foundChar;
    }
}
