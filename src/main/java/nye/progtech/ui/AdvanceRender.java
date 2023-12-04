package nye.progtech.ui;

import java.util.HashMap;
import java.util.Map;

import nye.progtech.model.Arrow;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdvanceRender implements BoardRender {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassicRender.class);
    public static final Map<Character, Character> mapRepresentation = new HashMap<>();
    public static final Map<Character, Character> heroRepresentation = new HashMap<>();
    public static final Map<Character, Character> startRepresentation = new HashMap<>();
    public static final Map<Character, Character> arrowRepresentation = new HashMap<>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GRAY = "\u001B[47m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    static {
        mapRepresentation.put('W', '█');
        mapRepresentation.put('G', 'G');
        mapRepresentation.put('U', 'W');
        mapRepresentation.put('P', 'P');
        mapRepresentation.put('_', ' ');
    }

    static {
        arrowRepresentation.put('N', '^');
        arrowRepresentation.put('E', '>');
        arrowRepresentation.put('W', '<');
        arrowRepresentation.put('S', 'v');
    }

    static {
        startRepresentation.put('S', 'X');
    }

    static {
        heroRepresentation.put('N', '↑');
        heroRepresentation.put('E', '→');
        heroRepresentation.put('W', '←');
        heroRepresentation.put('S', '↓');
    }

    @Override
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void printBoard(GameState gameState, Arrow arrow) {
        this.clearConsole();
        int[] arrowPosition;
        char arrowDirection;
        if (arrow == null) {
            arrowPosition = new int[]{100, 100};
            arrowDirection = 'E';
        } else {
            arrowPosition = arrow.getPosition();
            arrowDirection = arrow.getDirection();
        }
        char[][] map = gameState.getBoard().getMap();
        Hero hero = gameState.getHero();
        int[] startPosition = gameState.getStartPosition();

        StringBuilder boardBuilder = new StringBuilder();
        StringBuilder legendBuilder = new StringBuilder();


        for (int i = 0; i < map.length; i++) {
            boardBuilder.append(String.format("%2d%2s", i + 1, ""));


            for (int j = 0; j < map[0].length; j++) {
                boolean continousWall = j != map[0].length - 1 && map[i][j] == 'W' && map[i][j + 1] == 'W';
                String space = continousWall ? "" : " ";
                if (i == hero.getPosition()[0] && j == hero.getPosition()[1]) {
                    boardBuilder.append(ANSI_GREEN).append(heroRepresentation.get(hero.getDirection())).append(ANSI_RESET).append(" ");
                } else if (i == arrowPosition[0] && j == arrowPosition[1]) {
                    boardBuilder.append(ANSI_GREEN).append(arrowRepresentation.get(arrowDirection)).append(ANSI_RESET).append(" ");
                } else if (i == startPosition[0] && j == startPosition[1]) {
                    boardBuilder.append(ANSI_GREEN).append(startRepresentation.get('S')).append(ANSI_RESET).append(" ");
                } else {
                    char mapElement = mapRepresentation.get(map[i][j]);
                    switch (mapElement) {
                        case '█':
                            String wallChar = ANSI_GRAY + mapElement + ANSI_RESET;
                            if (continousWall) {
                                boardBuilder.append(wallChar).append(wallChar);
                            } else {
                                boardBuilder.append(wallChar).append(space);
                            }
                            break;
                        case 'W':
                            boardBuilder.append(ANSI_RED).append(mapElement).append(ANSI_RESET).append(" ");
                            break;
                        case 'G':
                            boardBuilder.append(ANSI_YELLOW).append(mapElement).append(ANSI_RESET).append(" ");
                            break;
                        case 'P':
                            boardBuilder.append(ANSI_BLUE).append(mapElement).append(ANSI_RESET).append(" ");
                            break;
                        default:
                            boardBuilder.append(mapElement).append(" ");
                            break;
                    }
                }
            }
            boardBuilder.append("\n");
        }


        legendBuilder.append("Board Legend:\n█=Wall, W=Wumpus, G=Gold, P=Pit, ↑↓→←=Hero\nX=Start, <^>v=Arrow");
        legendBuilder.append("\nControl:\nw=Move, a=Left turn, d=Right turn, s=Take gold\n' '=Shoot Arrow, q=Give up, p=Postpone");


        String[] boardLines = boardBuilder.toString().split("\n");
        String[] legendLines = legendBuilder.toString().split("\n");
        int maxBoardLineLength = boardLines.length > 0 ? boardLines[0].length() : 0;
        for (int i = 0; i < Math.max(boardLines.length, legendLines.length); i++) {
            if (i < boardLines.length) {
                System.out.print(boardLines[i]);
                System.out.printf("%8s", "");
            }
            if (i < legendLines.length) {
                System.out.print(legendLines[i]);
            }
            System.out.println();
        }


        printColumnLabels(map[0].length);


        StringBuilder statistics = new StringBuilder();
        statistics.append("\nStatistics:\nNumber of steps:\t").append(gameState.getNumberOfSteps())
                .append("\nArrows left:\t\t").append(hero.getNumberOfArrows())
                .append("\nGold acquired:\t").append(hero.getHasGold());
        System.out.println(statistics.toString());
    }

    private void printColumnLabels(int size) {
        System.out.printf("%4s", "");
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
    }


}
