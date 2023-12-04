package nye.progtech.exceptions;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouldNotReachDatabaseTest {

    private  CouldNotReachDatabase underTest;

    @Test
    void testCouldNotReachDatabaseShouldReturnTheMessageWhenCalled() {
        //Given
        String expectedMessage = "Database is unreachable";
        underTest = new CouldNotReachDatabase(expectedMessage, new RuntimeException("Cause"));
        //When - Then
        assertEquals(expectedMessage, underTest.getMessage());
    }

    @Test
    void testCouldNotReachDatabaseShouldReturnTheCauseWhenCalled() {
        //Given
        RuntimeException cause = new RuntimeException("Cause");
        underTest = new CouldNotReachDatabase("Database is unreachable", cause);
        //When - Then
        assertEquals(cause, underTest.getCause());
    }
}