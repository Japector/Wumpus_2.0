package nye.progtech.model;

import java.util.Arrays;

public class Arrow {

    private final int[] position;
    private final char direction;
    private final int speed;

    public Arrow(int[] position,  char direction, int speed) {
        this.position = deepCopyIntArray(position);
        this.direction = direction;
        this.speed = speed;
    }

    public int[] getPosition() {
        return deepCopyIntArray(position);
    }

    public char getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    private int[] deepCopyIntArray(int[] input) {
        int[] deepCopied = null;
        if (input != null) {
            deepCopied = Arrays.copyOf(input, input.length);
        }
        return deepCopied;
    }
}
