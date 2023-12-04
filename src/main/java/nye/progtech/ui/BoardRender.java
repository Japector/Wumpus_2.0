package nye.progtech.ui;

import nye.progtech.model.Arrow;
import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;

public interface BoardRender {
    void  clearConsole();

    void  printBoard(GameState gameState, Arrow arrow);


}
