package nye.progtech.service.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroUtil {
    public static final List<Character> ROTATION_MAP = new ArrayList<>(List.of('N', 'E', 'S', 'W'));
    public static final Map<Character, Integer> ROTATION_KEY = new HashMap<>();

    static {
        ROTATION_KEY.put('a', -1);
        ROTATION_KEY.put('d', 1);
    }

    public  char rotateHero(char turningDirection, char oldDirection) {
        int numDirection = ROTATION_MAP.indexOf(oldDirection) + ROTATION_KEY.get(turningDirection);
        return ROTATION_MAP.get(numDirection < 0 ? 3 : numDirection % 4);
    }


    public int[] moveForward(int[] oldPosition, char direction) {
        int[]  newPosition = oldPosition;
        switch (direction) {
            case 'N' -> newPosition[0]--;
            case 'S' -> newPosition[0]++;
            case 'E' -> newPosition[1]++;
            case 'W' -> newPosition[1]--;
            default -> System.out.println("Error");
        }
        return newPosition;
    }

}
