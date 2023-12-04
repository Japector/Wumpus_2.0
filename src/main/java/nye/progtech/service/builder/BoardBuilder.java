package nye.progtech.service.builder;

import java.util.List;

import nye.progtech.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BoardBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardBuilder.class);
    private char[][] map = null;
    private int size;

    public BoardBuilder() {}

    public Board builder(List<String> listOfLines) {
        LOGGER.info("Board builder creating a map using list of lines:\n{}", listOfLines);
        for (int i = 0; i < listOfLines.size(); i++) {
            if (i == 0) {
                size = Integer.parseInt(listOfLines.get(i).split(" ")[0]);
                map = new char[size][size];
            } else {
                map[i - 1] = listOfLines.get(i).toCharArray();
            }
        }

        return new Board(this.size, this.map);
    }

    public Board builder(String flatmap, int size) {
        LOGGER.info("Board builder creating a map using flatmap:\n{}", flatmap);
        char[][] map = new char[size][size];
        for (int i = 0; i < size; i++) {
            map[i] = flatmap.substring(i * size, (i + 1) * size).toCharArray();
        }
        return new Board(size, map);
    }
}
