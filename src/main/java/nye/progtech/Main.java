package nye.progtech;

import java.util.Scanner;

import nye.progtech.service.command.InputHandler;
import nye.progtech.service.game.GamePlay;


public class Main {
    public static void main(String[] args) {
        GamePlay gamePlay = new GamePlay(new Scanner(System.in));
        gamePlay.InitGame();
    }
}