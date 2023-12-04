package nye.progtech.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidInputTest {

    @Test
    void testInvalidInputShouldReturnTheMessageWhenCalled() {
        //Given
        InvalidInput underTest;
        String expectedMessage = "Invalid input";
        underTest = new InvalidInput(expectedMessage);
        //When - Then
        assertEquals(expectedMessage, underTest.getMessage());
    }

}