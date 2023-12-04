package nye.progtech.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeroUtilTest {

    HeroUtil underTest = new HeroUtil();

    @Test
    void testRotateHeroShouldReturnTheCorrectDirectionWhenRotatedLeft() {
        //Given
        char currentDirection = 'E';
        char turningDirection = 'a'; //left
        char expected = 'N';
        //When
        char actual = underTest.rotateHero(turningDirection, currentDirection);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testRotateHeroShouldReturnTheCorrectDirectionWhenRotatedRight() {
        //Given
        char currentDirection = 'E';
        char turningDirection = 'd'; //left
        char expected = 'S';
        //When
        char actual = underTest.rotateHero(turningDirection, currentDirection);
        //Then
        Assertions.assertEquals(expected, actual);

    }

    //moveForward
    @Test
    void testMoveForwardShouldMoveInTheCorrectDirectionWhenFacingNorth() {
        //Given
        char direction = 'N';
        int[] oldPosition = {5, 5}, expected = {4, 5};
        //When
        int [] actual = underTest.moveForward(oldPosition, direction);
        //Then
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testMoveForwardShouldMoveInTheCorrectDirectionWhenFacingEast() {
        //Given
        char direction = 'E';
        int[] oldPosition = {5, 5}, expected = {5, 6};
        //When
        int [] actual = underTest.moveForward(oldPosition, direction);
        //Then
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testMoveForwardShouldMoveInTheCorrectDirectionWhenFacingSouth() {
        //Given
        char direction = 'S';
        int[] oldPosition = {5, 5}, expected = {6, 5};
        //When
        int [] actual = underTest.moveForward(oldPosition, direction);
        //Then
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testMoveForwardShouldMoveInTheCorrectDirectionWhenFacingWest() {
        //Given
        char direction = 'W';
        int[] oldPosition = {5, 5}, expected = {5, 4};
        //When
        int [] actual = underTest.moveForward(oldPosition, direction);
        //Then
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testMoveForwardShouldNotMoveWhenIncorrectDirectionIsGiven() {
        //Given
        char direction = 'T';
        int[] expected = {5, 5};
        //When
        int [] actual = underTest.moveForward(expected, direction);
        //Then
        Assertions.assertArrayEquals(expected, actual);
    }
}