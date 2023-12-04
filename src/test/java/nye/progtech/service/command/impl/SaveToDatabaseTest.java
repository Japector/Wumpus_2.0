package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SaveToDatabaseTest {


    private SaveToDatabase underTest;
    private GameState expected;
    private String gameStateFlatMap;
    private GameRepository mockGameRepository;

    @BeforeEach
    public void setup() {
        List<String> gameStateInput = new ArrayList<>(
                List.of("testUser 0 B 5 false ",
                        "B 4 N 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        gameStateFlatMap = String.join("", gameStateInput);
        expected = new GameStateBuilder().builder(gameStateFlatMap);
        expected.setUserName("testUser");
        mockGameRepository = mock(GameRepository.class);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        String input ="4";
        boolean expected = true;
        underTest = new SaveToDatabase(mockGameRepository);
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCanProcessShouldReturnFalseWhenGetsTheInCorrectInput() {
        //Given
        String input ="m";
        boolean expected = false;
        underTest = new SaveToDatabase(mockGameRepository);
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testProcessShouldCorrectlyProcessTheGameWhenCalled() {

        //Given
        underTest = new SaveToDatabase(mockGameRepository);

        //When
        GameState actual = underTest.process(expected);

        //Then
        Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
        Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
        Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
        Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());


        Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
        Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

        Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
        Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
        Assertions.assertEquals(expected.isFinishedGame(),actual.isFinishedGame());
        Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());

    }
}