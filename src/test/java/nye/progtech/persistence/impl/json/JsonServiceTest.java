package nye.progtech.persistence.impl.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class JsonServiceTest {

    private static final File JSON_FILE = new File("GameStateDatabase.json");
    private JsonService underTest;


    @BeforeEach
    public void setupConnection() throws IOException {
        if (!JSON_FILE.exists()) {
            String jsonContent = "{\n" +
                    "\"gameStateMap\" : {\n" +
                    "\t\"1\" : \"user1 0 C 12 false C 12 E 3 false 15 WWWWWWWWWWWWWWWW____W________WW____W___U____WW____W________WW____P________WW_____W___G___WW__U__W_W_____WW__________P__WW_______W_____WW___W___U_____WW___W_________WW___WWWWWW____WW_____P__W____WW_P______W____WWWWWWWWWWWWWWWW\"\n" +
                    "},\n\"nextId\" : 2}";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE))) {
                writer.write(jsonContent);
            }
        }
    }



    @Test
    public void testGetInstanceShouldReturnTheSameReferenceWhenCalled() {
        //Given - When
        underTest =  JsonService.getInstance();
        JsonService secondInstance = JsonService.getInstance();
        //Then
        assertSame(underTest, secondInstance);
    }

    @Test
    public void testGetDatabaseShouldReturnADatabaseWhenCalled() {
        //Given
        underTest =  JsonService.getInstance();
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(false);
        //When
        JsonDatabase actual = underTest.getDatabase();
        //Then
        assertNotNull(actual);
    }


    @Test
    public void testCreateJsonIfNotExistsShouldCreateAnXmlWhenItDoesNotExists() {
        //Given
        boolean deleted = JSON_FILE.delete();
        //When
        underTest = JsonService.getInstance();
        JsonDatabase actual = underTest.getDatabase();
        //Then
        assertNotNull(actual);
    }

    @Test
    public void testLoadDatabaseShouldReturnTheDatabaseWhenCalled() throws Exception {
        //Given
        underTest =  JsonService.getInstance();
        //When
        JsonDatabase actual = JsonService.loadDatabase();
        //Then
        assertNotNull(actual);
    }

    @Test
    public void testSaveDatabase() throws Exception {
        //Given
        underTest =  JsonService.getInstance();
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        ObjectWriter mockObjectWriter = mock(ObjectWriter.class);
        when(mockObjectMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockObjectWriter);
        underTest.setMapper(mockObjectMapper);
        //When
        underTest.saveDatabase();
        //Then
        verify(mockObjectMapper, times(1)).writerWithDefaultPrettyPrinter();
    }

}