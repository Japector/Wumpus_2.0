package nye.progtech.service.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import nye.progtech.model.GameState;

public class GameUtil {

    public static boolean isGameCompleted(int[] startPosition, int[] currentPosition, boolean hasGold) {
        return startPosition[0] == currentPosition[0] && startPosition[1] == currentPosition[1] && hasGold;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void scoreBoard(Map<Integer, GameState> gameStateList) {

        String title = "Scoreboard";
        int tableWidth = 50;
        int padding = (tableWidth - title.length()) / 2;
        String paddedTitle = String.format("%" + padding + "s%s%" + padding + "s", "", title, "");


        clearConsole();
        String horizontalLine = "+----------------------+---------------------------+";
        String headerFormat = "| %-20s | %-25s |\n";

        System.out.println(paddedTitle);
        System.out.println(horizontalLine);
        System.out.printf(headerFormat, "Player name", "Number of finished games");
        System.out.println(horizontalLine);
        Map<String, Long> scoreBoard = gameStateList.values()
                .stream()
                .filter(GameState::isFinishedGame)
                .collect(Collectors.groupingBy(GameState::getUserName, Collectors.counting()));

        String rowFormat = "| %-20s | %25d |\n";
        scoreBoard.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(c -> System.out.printf(rowFormat, c.getKey(), c.getValue()));

        System.out.println(horizontalLine);
        System.out.println("\n\nPress enter to continue...");
    }


    public static void printAvailableGames(String userName, Map<Integer, GameState> gameStateList) {

        String title = "Games available for load";
        int tableWidth = 120;
        int padding = (tableWidth - title.length()) / 2;
        String paddedTitle = String.format("%" + padding + "s%s%" + padding + "s", "", title, "");

        clearConsole();
        String horizontalLine = "+" + "-".repeat(tableWidth - 2) + "+";
        String headerFormat = "| %-10s | %-20s | %-20s | %-10s | %-20s | %-21s |\n";

        System.out.println(paddedTitle);
        System.out.println(horizontalLine);
        System.out.printf(headerFormat, "Game ID", "Player name", "Number of steps", "Gold", "Number of arrows", "Board size");
        System.out.println(horizontalLine);
        String rowFormat = "| %-10d | %-20s | %-20d | %-10b | %-20d | %-21d |\n";
        gameStateList.entrySet()
                .stream()
                .filter(c -> Objects.equals(c.getValue().getUserName(), userName))
                .filter(c -> !c.getValue().isFinishedGame() && c.getValue().getNumberOfSteps() > -1)
                .forEach(c -> System.out.printf(rowFormat, c.getKey(), c.getValue().getUserName(), c.getValue().getNumberOfSteps(),
                        c.getValue().getHero().getHasGold(), c.getValue().getHero().getNumberOfArrows(),
                        c.getValue().getBoard().getSize()));

        System.out.println(horizontalLine);
    }

}

