package nye.progtech.service.command.impl.ingame;

import java.util.Objects;
import java.util.Set;

import nye.progtech.WumpusCry;
import nye.progtech.model.Arrow;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import nye.progtech.service.command.Command;
import nye.progtech.service.util.HeroUtil;
import nye.progtech.service.util.MapUtil;
import nye.progtech.ui.AdvanceRender;
import nye.progtech.ui.BoardRender;
import nye.progtech.ui.ClassicRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShootArrow implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShootArrow.class);

    private final MapUtil mapUtil;
    private final HeroUtil heroUtil;
    private final BoardRender boardRender;
    private Arrow arrow;

    public ShootArrow(MapUtil mapUtil, HeroUtil heroUtil, BoardRender boardRender,  Arrow arrow) {
        this.mapUtil = mapUtil;
        this.heroUtil = heroUtil;
        this.arrow = arrow;
        this.boardRender = boardRender;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals(" ", input);
    }

    @Override
    public GameState process(GameState gameState) {
        int numberOfArrows = gameState.getHero().getNumberOfArrows();
        int[] position = gameState.getHero().getPosition();
        char direction = gameState.getHero().getDirection();
        char[][] map = gameState.getBoard().getMap();

        if (numberOfArrows > 0) {
            numberOfArrows = numberOfArrows - 1;
            LOGGER.info("Player ({}) decided to shoot an arrow from position: {}", gameState.getUserName(),
                    gameState.getHero().getPosition());
            arrow = new Arrow(heroUtil.moveForward(position, direction), direction, 2);
            LOGGER.info("Arrow cinematics started to play.");
            while (!Set.of('U', 'W').contains(mapUtil.getChar(arrow.getPosition(), map))) {
                try {
                    Thread.sleep(1000 / arrow.getSpeed());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                boardRender.printBoard(gameState, arrow);
                arrow = new Arrow(heroUtil.moveForward(position, direction), direction, 1);
            }
            Hero hero = gameState.getHero();
            gameState.setHero(new Hero(hero.getPosition(), hero.getDirection(), numberOfArrows, hero.getHasGold()));

            if (mapUtil.getChar(arrow.getPosition(), map) == 'U') {
                LOGGER.info("A wumpus died at positoin: {}", arrow.getPosition());
                gameState.setBoard(mapUtil.setPosition(arrow.getPosition(), '_', map));
                WumpusCry.wumpuszCry();
            }
        } else {
            System.out.println("You have no more arrows!");
        }
        return gameState;
    }
}
