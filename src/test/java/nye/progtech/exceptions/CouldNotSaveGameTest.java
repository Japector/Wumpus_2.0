package nye.progtech.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouldNotSaveGameTest {

    private  CouldNotSaveGame underTest;

    @Test
    void testCouldNotSaveGameShouldReturnTheMessageWhenCalled() {
        //Given
        String expectedMessage = "Could not save game";
        underTest = new CouldNotSaveGame(expectedMessage, new RuntimeException("Cause"));
        //When - Then
        assertEquals(expectedMessage, underTest.getMessage());
    }

    @Test
    void testCouldNotSaveGameShouldReturnTheCauseWhenCalled() {
        //Given
        RuntimeException cause = new RuntimeException("Cause");
        underTest = new CouldNotSaveGame("Could not save game", cause);
        //When - Then
        assertEquals(cause, underTest.getCause());
    }

}