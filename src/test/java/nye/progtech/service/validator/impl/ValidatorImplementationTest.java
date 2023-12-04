package nye.progtech.service.validator.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class ValidatorImplementationTest {

    private final ValidatorImplementation underTest = new ValidatorImplementation();
    private static final String FAILING_FIRST_LINE = "14 Z 21 E";
    private static final String PASSING_FIRST_LINE = "15 C 12 E";
    private static final List<String> PASSING_BOARD =
            List.of("WWWWWWWWWWWWWWW",
                                "W____W________W",
                                "W____W___U____W",
                                "W____W________W",
                                "W____P________W",
                                "W_____W___G___W",
                                "W__U__W_W_____W",
                                "W__________P__W",
                                "W_______W_____W",
                                "W___W___U_____W",
                                "W___W_________W",
                                "W___WWWWWW____W",
                                "W_____P__W____W",
                                "W_P______W____W",
                                "WWWWWWWWWWWWWWW");



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fileInputValidator() {
    }

    @Test
    void validateAll() {
    }

    //HeroLineValidator
    @Test
    void testHeroLineValidatorShouldReturnFalseWhenTheFirstLineIsIncorrect() {
        //Given
        boolean expected = false;
        //When
        boolean actual = ValidatorImplementation.heroLineValidator(FAILING_FIRST_LINE);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testHeroLineValidatorShouldReturnTrueWhenTheFirstLineIsIncorrect() {
        //Given
        boolean expected = true;
        //When
        boolean actual = ValidatorImplementation.heroLineValidator(PASSING_FIRST_LINE);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //boardSizeCheck
    @Test
    void testBoardSizeCheckShouldReturnFalseWhenTheBoardIsIncorrect() {
        //Given
        boolean expected = false;
        List<String> failingBoardSize = List.of("WWW","W_W","WWW");
        //When
        boolean actual = ValidatorImplementation.boardSizeCheck(PASSING_FIRST_LINE,failingBoardSize);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testBoardSizeCheckShouldReturnTrueWhenTheBoardIsIncorrect() {
        //Given
        boolean expected = true;
        //When
        boolean actual = ValidatorImplementation.boardSizeCheck(PASSING_FIRST_LINE,PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //boardFormatValidator
    @Test
    void testBoardFormatValidatorShouldReturnFalseWhenIllegalElementIsFound() {
        //Given
        boolean expected = false;
        List<String> illegalElementBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W_M__W",
                        "W__P_W",
                        "WWWWWW")
        );
        //When
        boolean actual = ValidatorImplementation.boardFormatValidator(illegalElementBoard);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testBoardFormatValidatorShouldReturnFalseWhenIllegalWallElementIsFound() {
        //Given
        boolean expected = false;
        List<String> illegalElementBoard = new ArrayList<>(
                List.of("WWJWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW")
        );
        //When
        boolean actual = ValidatorImplementation.boardFormatValidator(illegalElementBoard);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testBoardFormatValidatorShouldReturnTrueWhenOnlyAllowedElementsAreFound() {
        //Given
        boolean expected = true;
        //When
        boolean actual = ValidatorImplementation.boardFormatValidator(PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //heroBoardMismatchValidator
    @Test
    void tesHeroBoardMismatchValidatorShouldReturnFalseWhenThePositionOfTheHeroIsAlreadyOccupied() {
        //Given
        boolean expected = false;
        String occupiedHeroPosition = "15 K 6 E";
        //When
        boolean actual = ValidatorImplementation.heroBoardMismatchValidator(occupiedHeroPosition, PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tesHeroBoardMismatchValidatorShouldReturnTrueWhenThePositionOfTheHeroIsNotAlreadyOccupied() {
        //Given
        boolean expected = true;
        String occupiedHeroPosition = "15 K 6 E";
        //When
        boolean actual = ValidatorImplementation.heroBoardMismatchValidator(PASSING_FIRST_LINE, PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //hasGold
    @Test
    void testHasGoldShouldReturnFalseWhenThereIsNoGoldOnTheMap() {
        //Given
        boolean expected = false;
        List<String> noGoldBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "W___PW",
                        "WU_P_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW")
        );
        //When
        boolean actual = ValidatorImplementation.hasGold(noGoldBoard);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testHasGoldShouldReturnTrueWhenThereIsGoldOnTheMap() {
        //Given
        boolean expected = true;
        //When
        boolean actual = ValidatorImplementation.hasGold(PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //wumpusCheck
    @Test
    void testWumpusCheckShouldReturnFalseWhenTheNumberOfWumpusIsIncorrectOnTheMap() {
        //Given
        boolean expected = false;
        String firstLint = "6 B 5 E";
        List<String> incorrectWumpusBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "W___PW",
                        "W_GP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW")
        );
        //When
        boolean actual = ValidatorImplementation.wumpusCheck(firstLint,incorrectWumpusBoard);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //wumpusCheck
    @Test
    void testWumpusCheckShouldReturnTrueWhenTheNumberOfWumpusIsCorrectOnTheMap() {
        //Given
        boolean expected = true;
        //When
        boolean actual = ValidatorImplementation.wumpusCheck(PASSING_FIRST_LINE, PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }


    //validateAll
    @Test
    void testValidateAllShouldReturnFalseWhenAnyOfThePreviousTestFailsInTheNestedOrder() {
        //Given
        boolean expected = false;
        String firstLint = "6 B 5 E";
        List<String> incorrectBoard = new ArrayList<>(
                List.of("WWWWWW",
                        "W___PW",
                        "W_GP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW")
        );
        //When
        boolean actual = ValidatorImplementation.validateAll(firstLint, incorrectBoard);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testValidateAllShouldReturnTrueWhenAllOfThePreviousTestsPassInTheNestedOrder() {
        //Given
        boolean expected = true;
        //When
        boolean actual = ValidatorImplementation.validateAll(PASSING_FIRST_LINE, PASSING_BOARD);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    //fileInputValidator
    @Test
    void testFileInputValidatorShouldReturnFalseWhenItGetsAnEmptyList() {
        //Given
        List<String> emptyList = new ArrayList<>();
        boolean expected = false;
        //When
        boolean actual = underTest.fileInputValidator(emptyList);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFileInputValidatorShouldReturnTrueWhenItGetsACorrectInputListOfStrings() {
        //Given
        List<String> correctInput = new ArrayList<>();
        correctInput.add(PASSING_FIRST_LINE);
        correctInput.addAll(PASSING_BOARD);
        boolean expected = true;
        //When
        boolean actual = underTest.fileInputValidator(correctInput);
        //Then
        Assertions.assertEquals(expected, actual);
    }

}