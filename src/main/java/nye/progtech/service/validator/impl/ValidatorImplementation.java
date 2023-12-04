package nye.progtech.service.validator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nye.progtech.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ValidatorImplementation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorImplementation.class);
    static final String HERO_LINE_FORMAT = "([6-9]|1[0-9]|20) [A-T] ([2-9]|1[0-9]) [NEWS]";

    public boolean fileInputValidator(List<String> listOfLines) {
        LOGGER.info("Input validator was called to validate input:\n{}", listOfLines);
        if (listOfLines.size() == 0) {
            return false;
        }
        List<String> consumableList;
        consumableList = listOfLines.stream().map(String::new).collect(Collectors.toList());
        String heroLine = consumableList.get(0);
        consumableList.remove(0);
        return validateAll(heroLine, consumableList);
    }


    public static boolean validateAll(String heroLine, List<String> listOfLines) {
        List<Validator> validators = new ArrayList<>();


        validators.add(() -> heroLineValidator(heroLine));
        validators.add(() -> boardSizeCheck(heroLine, listOfLines));
        validators.add(() -> boardFormatValidator(listOfLines));
        validators.add(() -> heroBoardMismatchValidator(heroLine, listOfLines));
        validators.add(() -> hasGold(listOfLines));
        validators.add(() -> wumpusCheck(heroLine, listOfLines));


        for (Validator validator : validators) {
            if (!validator.validate()) {
                return false;
            }
        }

        return true;
    }

    public static boolean heroLineValidator(String heroLine) {
        if (!Pattern.matches(HERO_LINE_FORMAT, heroLine)) {
            LOGGER.error("Incorrect first (Hero) line");
            System.out.println("Incorrect first (Hero) line");
            return false;
        }
        return true;
    }

    public static boolean boardSizeCheck(String heroLine, List<String> linesOfBoard) {
        final int expectedSize = Integer.parseInt(heroLine.split(" ")[0]);
        if (linesOfBoard.size() != expectedSize || !linesOfBoard.stream().allMatch(line -> line.length() == expectedSize)) {
            LOGGER.error("The table format is not NxN");
            System.out.println("The table format is not NxN");
            return false;
        }
        return true;
    }

    public static boolean boardFormatValidator(List<String> linesOfBoard) {
        int size = linesOfBoard.size();


        if (!linesOfBoard.get(0).matches("W{" + size + "}") || !linesOfBoard.get(size - 1).matches("W{" + size + "}")) {
            LOGGER.error("The first and last row can only contain W");
            System.out.println("The first and last row can only contain W");
            return false;
        }


        for (int i = 1; i < size - 1; i++) {
            String line = linesOfBoard.get(i);
            if (!line.matches("W[W_PUG]*W") || line.length() != size) {
                LOGGER.error("Wrong format in line: " + (i + 1));
                System.out.println("Wrong format in line: " + (i + 1));
                return false;
            }
        }
        return true;
    }

    public static boolean heroBoardMismatchValidator(String heroLine, List<String> linesOfBoard) {
        int[] heroPosition = new int[2];
        String[] heroArray = heroLine.split(" ");
        heroPosition[0] = Integer.parseInt(heroArray[2]) - 1;
        heroPosition[1] = heroArray[1].charAt(0) - 'A';
        char[] elementsInHeroLine = linesOfBoard.get(heroPosition[0]).toCharArray();
        if (List.of('U', 'G', 'P', 'W').contains(elementsInHeroLine[heroPosition[1]])) {
            LOGGER.error("The hero position is in conflict with the map");
            System.out.println("The hero position is in conflict with the map");
            return false;
        }
        return true;
    }

    public static boolean hasGold(List<String> linesOfBoard) {
        if (linesOfBoard.stream()
                    .flatMapToInt(String::chars)
                    .filter(c -> c == 'G')
                    .count() != 1L) {
            LOGGER.error("There is no gold on the map.");
            System.out.println("There is no gold on the map.");
            return false;
        }
        return true;
    }

    public static boolean wumpusCheck(String heroLine, List<String> linesOfBoard) {
        final int size = Integer.parseInt(heroLine.split(" ")[0]);
        int expectedWumpus = 0;
        if (size <= 8) {
            expectedWumpus = 1;
        } else if (size <= 14) {
            expectedWumpus = 2;
        } else {
            expectedWumpus = 3;
        }
        if (linesOfBoard.stream()
                .flatMapToInt(String::chars)
                .filter(c -> c == 'U')
                .count() != (long) expectedWumpus) {
            LOGGER.error("The number of wumpus on the map is incorrect");
            System.out.println("The number of wumpus on the map is incorrect");
            return false;
        }
        return true;
    }
}
