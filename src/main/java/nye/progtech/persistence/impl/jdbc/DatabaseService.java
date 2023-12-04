package nye.progtech.persistence.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

import nye.progtech.exceptions.CouldNotReachDatabase;
import nye.progtech.service.input.InputProcessing;


public class DatabaseService implements DatabaseServiceInterface {


    private static DatabaseService instance;


    private DatabaseService() {}

    public static DatabaseService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DatabaseService();
            createTableIfNotExists(instance.getConnection());
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/wumpusz", "admin", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableIfNotExists(Connection connection) {
            String createString = InputProcessing.readResource("sql/sql_create.sql").collect(Collectors.joining());
            if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(createString);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }
        }
    }

}


