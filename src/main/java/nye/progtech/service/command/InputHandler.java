package nye.progtech.service.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nye.progtech.model.Arrow;
import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.command.impl.CreateMap;
import nye.progtech.service.command.impl.Default;
import nye.progtech.service.command.impl.LoadFromDatabase;
import nye.progtech.service.command.impl.QuitGame;
import nye.progtech.service.command.impl.ReadMapFromFile;
import nye.progtech.service.command.impl.SaveToDatabase;
import nye.progtech.service.command.impl.StartGame;
import nye.progtech.service.command.impl.createmap.AddElement;
import nye.progtech.service.command.impl.createmap.Finish;
import nye.progtech.service.command.impl.createmap.RemoveElement;
import nye.progtech.service.command.impl.createmap.SetDirection;
import nye.progtech.service.command.impl.ingame.GiveUpGame;
import nye.progtech.service.command.impl.ingame.MoveForward;
import nye.progtech.service.command.impl.ingame.PostponeGame;
import nye.progtech.service.command.impl.ingame.ShootArrow;
import nye.progtech.service.command.impl.ingame.TakeGold;
import nye.progtech.service.command.impl.ingame.TurnLeft;
import nye.progtech.service.command.impl.ingame.TurnRight;
import nye.progtech.service.input.InputProcessing;
import nye.progtech.service.util.HeroUtil;
import nye.progtech.service.util.MapUtil;
import nye.progtech.ui.AdvanceRender;
import nye.progtech.ui.BoardRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputHandler.class);
    private final List<Command> commands;
    private GameState gameState;

    public InputHandler(GameRepository gameRepository, Scanner scanner) {
        BoardRender boardRender = new AdvanceRender();
        MapUtil mapUtil = new MapUtil();
        HeroUtil heroUtil = new HeroUtil();
        commands =  new ArrayList<>(List.of(new CreateMap(scanner, boardRender, this),
                                            new ReadMapFromFile(scanner, new InputProcessing()),
                                            new LoadFromDatabase(scanner, gameRepository),
                                            new SaveToDatabase(gameRepository),
                                            new StartGame(boardRender, scanner, this, gameRepository),
                                            new QuitGame(),
                                            new GiveUpGame(),
                                            new PostponeGame(),
                                            new MoveForward(mapUtil, heroUtil),
                                            new ShootArrow(mapUtil, heroUtil, boardRender,
                                                            new Arrow(new int[] {0, 0}, 'N', 1)),
                                            new TakeGold(mapUtil),
                                            new TurnLeft(heroUtil),
                                            new TurnRight(heroUtil),
                                            new AddElement(mapUtil),
                                            new RemoveElement(mapUtil),
                                            new Finish(),
                                            new SetDirection(),
                                            new Default()));
    }



    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState handleInput(String input) {
        if (input.length() > 0) {
            for (Command command : commands) {
                if (command.canProcess(input)) {
                    LOGGER.info("The input handler was invoked to handle command {}", input);
                    gameState = command.process(this.getGameState());
                    break;
                }
            }
        }
        return gameState;
    }
}
