package nye.progtech.persistence.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import nye.progtech.exceptions.CouldNotReachDatabase;
import nye.progtech.exceptions.CouldNotSaveGame;
import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.builder.GameStateBuilder;


public class DatabaseHandler implements GameRepository {
    private final DatabaseServiceInterface databaseService;

    public DatabaseHandler(DatabaseServiceInterface databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void saveGame(GameState gameState) {

        String statement = "INSERT INTO SAVED_GAMES (PLAYER,SCORE,FINISHED,GAMESTATE) " + "VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, gameState.getUserName());
            preparedStatement.setInt(2, gameState.getNumberOfSteps());
            preparedStatement.setBoolean(3, gameState.isFinishedGame());
            preparedStatement.setString(4, gameState.toString());
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new CouldNotSaveGame("Could not save game: " + statement, ex);
        }
    }

    @Override
    public GameState loadGame(String userName, int gameID) {

        String statement = "SELECT * FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, gameID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String gameStateString = resultSet.getString("GAMESTATE");
            int score = resultSet.getInt("SCORE");
            boolean finished = resultSet.getBoolean("FINISHED");
            GameState gameState = new GameStateBuilder().builder(gameStateString);
            if (Objects.equals(gameState.getUserName(), userName) && score > -1 && !finished) {
                System.out.println("Game successfully loaded!");
                return gameState;
            } else {
                System.out.println("Game cannot be loaded as it is either finished or failed.");
            }
        } catch (SQLException ex) {
            System.out.println("Prepared statement failed: " + statement);
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean checkGameExists(String userName, int gameID) {
        String statement = "SELECT * FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, gameID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            System.out.println("Game play existence could not be checked.");
            return false;
        }
    }

    @Override
    public Map<Integer, GameState> returnGames() {
        Map<Integer, GameState> gameStateList = new HashMap<>();
        String statement = "SELECT ID, GAMESTATE FROM SAVED_GAMES";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String gameState = resultSet.getString("GAMESTATE");
                gameStateList.put(id, new GameStateBuilder().builder(gameState));
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong in the scoreboard compilation");
        }
        return gameStateList;
    }
}
