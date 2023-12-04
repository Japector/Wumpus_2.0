package nye.progtech.service.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InputProcessingTest {

    private final InputProcessing underTest = new InputProcessing();
    //InputProcessing
    @Test
    public void testFileInputShouldReturnTheStringStreamWhenCalledWithExistingContent(@TempDir Path tempDir) throws Exception {
        //Given
        File tempFile = tempDir.resolve("testFile.txt").toFile();
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println("Line 1");
            out.println("Line 2");
        }

        //When
        Stream<String> actualStream = underTest.fileInput(tempFile);
        List<String> actual = actualStream.toList();

        //Then
        assertEquals(2, actual.size(), "The stream should contain two lines.");
        assertEquals("Line 1", actual.get(0), "First line should match.");
        assertEquals("Line 2", actual.get(1), "Second line should match.");
    }

    @Test
    public void testFileInputShouldReturnTheStringStreamWhenCalledWithNonExistingContent(@TempDir Path tempDir) throws Exception {
        //Given
        File nonExistent = new File("test");
        int expected = 0;

        //When
        Stream<String> actualStream = underTest.fileInput(nonExistent);
        List<String> actual = actualStream.toList();

        //Then
        assertEquals(expected, actual.size(), "The stream should contain two lines.");

    }

    //readResource
    @Test
    void testReadResourceShouldReturnSqlCreateScriptWhenCalledWithExistentResource() {
        //Given
        List<String> expectedList = List.of(
                "CREATE TABLE IF NOT EXISTS SAVED_GAMES(",
                        "ID int PRIMARY KEY AUTO_INCREMENT,",
                        "PLAYER varchar(255) NOT NULL,",
                        "FINISHED boolean NOT NULL,",
                        "SCORE int NOT NULL,",
                        "GAMESTATE varchar(420) NOT NULL);",
                        "",
                        "CREATE TABLE IF NOT EXISTS SCORE_BOARD(",
                        "ID int PRIMARY KEY AUTO_INCREMENT,",
                        "PLAYER varchar(255) NOT NULL,",
                        "SCORE INT NOT NULL",
                        ");");
        String expected = String.join("", expectedList);

        //When
        Stream<String> actualStream = InputProcessing.readResource("sql/sql_create.sql");
        String actual = actualStream.collect(Collectors.joining());

        //Then
        Assertions.assertEquals(expected, actual);
        }

    @Test
    void testReadResourceShouldThrowRunTimeExceptionWhenCalledWithNonExistentResource() {
        //When--Then
        Assertions.assertThrows(RuntimeException.class,() -> InputProcessing.readResource("nonExistentResource"));
    }
}
