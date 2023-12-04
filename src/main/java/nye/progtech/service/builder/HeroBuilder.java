package nye.progtech.service.builder;

import java.util.List;

import nye.progtech.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HeroBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeroBuilder.class);

    public Hero builder(List<String> listOfLines) {
        //List<String> listOfLines = data.toList();
        LOGGER.info("Hero builder creating a hero using list of lines:\n{}", listOfLines);
        String[] heroLine = listOfLines.get(0).split(" ");
        int[] position = new int[2];
        position[0] = Integer.parseInt(heroLine[2]) - 1;
        position[1] = heroLine[1].charAt(0) - 'A';
        char direction = heroLine[3].charAt(0);
        boolean hasGold = Boolean.getBoolean(heroLine[4]);
        int numberOfArrows = (int) listOfLines.stream()
                .flatMapToInt(String::chars)
                .filter(c -> c == 'U')
                .count();
        return new Hero(position, direction, numberOfArrows, hasGold);
    }

    public Hero builder(String heroLine) {
        LOGGER.info("Board builder creating a map using flatmap:\n{}", heroLine);
        String[] heroStrings = heroLine.split(" ");
        int[] position = new int[2];
        position[0] = Integer.parseInt(heroStrings[1]) - 1;
        position[1] = heroStrings[0].charAt(0) - 'A';
        char direction = heroStrings[2].charAt(0);
        int numberOfArrows = Integer.parseInt(heroStrings[3]);
        boolean hasGold = heroStrings[4].equals("true");
        return new Hero(position, direction, numberOfArrows, hasGold);
    }
}
