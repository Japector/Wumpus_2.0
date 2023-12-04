package nye.progtech.service.builder;

import nye.progtech.model.Hero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class HeroBuilderTest {
    private HeroBuilder underTest = new HeroBuilder();


    @Test
    void testBuilderShouldGenerateTheSameHeroWhenCalledWithTheInput() {
        //Given
        List<String> heroInput = new ArrayList<>(
                List.of("6 B 5 E false",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));

        Hero expected = new Hero(new int[] {4,1}, 'E', 1, false);

        //When
        Hero actual = underTest.builder(heroInput);

        //Then
        Assertions.assertArrayEquals(expected.getPosition(), actual.getPosition());
        Assertions.assertEquals(expected.getDirection(), actual.getDirection());
        Assertions.assertEquals(expected.getNumberOfArrows(), actual.getNumberOfArrows());
        Assertions.assertEquals(expected.getHasGold(), actual.getHasGold());
    }

    @Test
    void testBuilderShouldGenerateTheCorrectHeroWhenGivenAFlatInput() {
        //Given
        String heroLine = "B 5 E 1 false";
        Hero expected = new Hero(new int[] {4,1}, 'E', 1, false);

        //When
        Hero actual = underTest.builder(heroLine);

        //Then
        Assertions.assertArrayEquals(expected.getPosition(), actual.getPosition());
        Assertions.assertEquals(expected.getDirection(), actual.getDirection());
        Assertions.assertEquals(expected.getNumberOfArrows(), actual.getNumberOfArrows());
        Assertions.assertEquals(expected.getHasGold(), actual.getHasGold());

    }
}