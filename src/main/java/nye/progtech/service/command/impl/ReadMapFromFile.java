package nye.progtech.service.command.impl;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.BoardBuilder;
import nye.progtech.service.builder.HeroBuilder;
import nye.progtech.service.command.Command;
import nye.progtech.service.input.InputProcessing;
import nye.progtech.service.util.GameUtil;
import nye.progtech.service.validator.impl.ValidatorImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReadMapFromFile implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadMapFromFile.class);
    private final Scanner scanner;
    private final ValidatorImplementation validatorImplementation = new ValidatorImplementation();
    private final InputProcessing inputProcessing;

    public ReadMapFromFile(Scanner scanner, InputProcessing inputProcessing) {
        this.scanner = scanner;
        this.inputProcessing = inputProcessing;
    }

    @Override
    public boolean canProcess(String input) {
        return Objects.equals("2", input);
    }

    @Override
    public GameState process(GameState gameState) {

        GameUtil.clearConsole();
        String userName = gameState.getUserName();
        System.out.println("Please enter the path to the board:");
        String path = scanner.nextLine();
        LOGGER.info("Reading map from file using the path: {}", path);
        List<String> listStream = inputProcessing.fileInput(new File(path))
                                                .map(c -> c.replace("H", "_"))
                                                .collect(Collectors.toList());
        if (validatorImplementation.fileInputValidator(listStream)) {
            listStream.set(0, listStream.get(0) + " false"); //Gold injection
            LOGGER.info("Setting up game state based on the file input: {}", listStream);
            gameState = new GameState(new BoardBuilder().builder(listStream), new HeroBuilder().builder(listStream));
            gameState.setUserName(userName);
        }
        return gameState;
    }
}
