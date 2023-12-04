package nye.progtech.service.builder;

import nye.progtech.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class BoardBuilderTest {

    private BoardBuilder underTest = new BoardBuilder();


    @Test
    void testBuilderShouldGenerateTheSameMapWhenCalledWithTheInput() {
        //Given
        List<String> boardInput = new ArrayList<>(
                List.of("6 B 5 E",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));

        List<String> expectedBoard =  boardInput.stream().map(String::new).collect(Collectors.toList());
        expectedBoard.remove(0);
        char[][] expected = new char[expectedBoard.size()][];

        for (int i = 0; i < expectedBoard.size(); i++) {
            expected[i] = expectedBoard.get(i).toCharArray();
        }
        //When
        Board actual = underTest.builder(boardInput);
        //Then
        Assertions.assertArrayEquals(expected,actual.getMap());
    }

    @Test
    void testBuilderShouldGenerateTheCorrectMapWhenGivenAFlatMapAsInput() {
        //Given
        List<String> boardInput = List.of( "WWWWWW",
                                        "W___PW",
                                        "WUGP_W",
                                        "W____W",
                                        "W__P_W",
                                        "WWWWWW");

        char[][] expected = new char[boardInput.size()][];
        for (int i = 0; i < boardInput.size(); i++) {
            expected[i] = boardInput.get(i).toCharArray();
        }

        //When
        Board actual = underTest.builder(String.join("", boardInput), boardInput.size());
        //Then
        Assertions.assertArrayEquals(expected,actual.getMap());

    }
}