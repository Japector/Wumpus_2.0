package nye.progtech.model;



import java.util.Arrays;


public class Hero {
    private final int[] position;
    private final char direction;
    private final boolean hasGold;
    private final int numberOfArrows;


    public Hero(int[] position, char direction, int numberOfArrows, boolean hasGold) {
        this.position = deepCopyIntArray(position);
        this.direction = direction;
        this.numberOfArrows = numberOfArrows;
        this.hasGold = hasGold;
    }

    public int[] getPosition() {
        return deepCopyIntArray(position);
    }

    public char getDirection() {
        return direction;
    }

    public boolean getHasGold() {
        return hasGold;
    }

    public int getNumberOfArrows() {
        return numberOfArrows;
    }

    private int[] deepCopyIntArray(int[] input) {
        int[] deepCopied = null;

        if (input != null) {
            deepCopied = Arrays.copyOf(input, input.length);
        }
        return deepCopied;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder((char) (this.getPosition()[1] + 'A') + " ");
        sb.append((this.getPosition()[0] + 1)).append(" ");
        sb.append(this.getDirection()).append(" ");
        sb.append(this.getNumberOfArrows()).append(" ");
        sb.append(this.getHasGold());
        return sb.toString();
    }


}
