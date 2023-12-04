package nye.progtech.model;


import java.util.Arrays;


public class Board {

    private final char[][] map;
    private final int size;

    public Board(int size, char[][] map) {
        this.size = size;
        this.map = deepCopy(map);
    }

    public char[][] getMap() {
        return deepCopy(map);
    }

    public int getSize() {
        return size;
    }

    private char[][] deepCopy(char[][] input) {
        char[][] deepCopied = null;

        if (input != null) {
            deepCopied = new char[input.length][];
            for (int i = 0; i < input.length; i++) {
                deepCopied[i] = Arrays.copyOf(input[i], input[i].length);
            }
        }
        return deepCopied;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getSize() + " ");
        for (char[] row : getMap()) {
            sb.append(String.valueOf(row));
        }
        return sb.toString();
    }

}
