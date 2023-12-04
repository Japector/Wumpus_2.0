package nye.progtech.model;


public class GameState {
    private Board board;
    private Hero hero;
    private int[] startPosition;
    private int numberOfSteps;
    private String userName;
    private boolean finishedGame;

    private boolean postponeGame = false;
    private boolean givenUpGame = false;
    private boolean stillPlaying = true;


    public GameState(String userName, Board board, Hero hero, int numberOfSteps, int[] startPosition, boolean finishedGame) {
        this.userName = userName;
        this.board = board;
        this.hero = hero;
        this.numberOfSteps = numberOfSteps;
        this.startPosition = startPosition;
        this.finishedGame = finishedGame;
    }

    public GameState(Board board, Hero hero) {
        this.board = board;
        this.hero = hero;
        this.startPosition = hero.getPosition();
        this.numberOfSteps = 0;
        this.finishedGame = false;
    }

    public GameState(String userName) {
        this.userName = userName;
    }


    //Getter
    public Board getBoard() {
        return board;
    }

    public Hero getHero() {
        return hero;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public int[] getStartPosition() {
        return startPosition;
    }

    public boolean isFinishedGame() {
        return finishedGame;
    }

    public boolean isStillPlaying() {
        return stillPlaying;
    }

    public boolean isGivenUpGame() {
        return givenUpGame;
    }

    public boolean isPostponeGame() {
        return postponeGame;
    }


    //Setter
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }

    public void setStillPlaying(boolean stillPlaying) {
        this.stillPlaying = stillPlaying;
    }

    public void setGivenUpGame(boolean givenUpGame) {
        this.givenUpGame = givenUpGame;
    }

    public void setPostponeGame(boolean postponeGame) {
        this.postponeGame = postponeGame;
    }

    public void setStartPosition(int[] startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getUserName() + " ");
        sb.append(this.getNumberOfSteps()).append(" ");
        sb.append((char) (this.getStartPosition()[1] + 'A')).append(" ");
        sb.append((this.getStartPosition()[0] + 1)).append(" ");
        sb.append(this.isFinishedGame()).append(" ");
        sb.append(hero.toString()).append(" ");
        sb.append(board.toString());
        return sb.toString();
    }
}
