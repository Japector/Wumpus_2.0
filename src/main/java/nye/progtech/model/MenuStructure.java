package nye.progtech.model;

import java.util.ArrayList;
import java.util.List;


public class MenuStructure {
    public static final List<String> MENU_LIST = new ArrayList<>(
            List.of("\t\tMenu",
                    "------------------------",
                    "(1) - Create new map",
                    "(2) - Read map from file",
                    "(3) - Load game from database",
                    "(4) - Save game into database",
                    "(5) - Play game",
                    "(6) - Quit"));


}
