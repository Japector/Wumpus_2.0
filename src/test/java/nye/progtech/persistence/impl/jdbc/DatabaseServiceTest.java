package nye.progtech.persistence.impl.jdbc;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.*;


class DatabaseServiceTest {


    private Connection connection;

    private Statement statement;

    private AutoCloseable closeable;



    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        connection =  mock(Connection.class);
        statement =  mock(Statement.class);;
    }

    @AfterEach
    void tearDown()  {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //SingletonTest
    @Test
    public void testGetInstanceShouldReturnTheSameInstanceWhenCalledMultipleTimes() {
        DatabaseService firstInstance = DatabaseService.getInstance();
        DatabaseService secondInstance = DatabaseService.getInstance();
        assertSame(firstInstance, secondInstance, "getInstance should always return the same instance");
    }

    //closeConnection
    @Test
    public void testCloseConnectionShouldCloseConnectionWhenCalled() throws SQLException {
        //Given
        //When
        DatabaseService.closeConnection(connection);
        //Then
        verify(connection, times(1)).close();
    }

    @Test
    public void testCloseConnectionShouldThrowExceptionWhenClosingIsFailed()  {
        try {
        //Given
        doThrow(new SQLException("Closing failed")).when(connection).close();
        //When
        DatabaseService.closeConnection(connection);
        //Then
        verify(connection, times(1)).close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testCreateTableIfNotExistsShouldThrowExceptionWhenStatementIsFailed()  {
        try {
        //Given
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        doThrow(new SQLException("Statement failed")).when(mockStatement).execute(anyString());
        //When-Then
        DatabaseService.createTableIfNotExists(mockConnection);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}