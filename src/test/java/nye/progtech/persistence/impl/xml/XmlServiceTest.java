package nye.progtech.persistence.impl.xml;

import nye.progtech.persistence.impl.json.JsonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XmlServiceTest {

    private static final File XML_FILE = new File("GameStateDatabase.xml");
    private XmlService underTest;

    @BeforeEach
    public void setupConnection() throws IOException {
        if (!XML_FILE.exists()) {
            String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<xmlDatabase>\n" +
                    "    <gameStateMap>\n" +
                    "        <entry>\n" +
                    "            <key>1</key>\n" +
                    "            <value>user1 0 C 12 false C 12 E 3 false 15 WWWWWWWWWWWWWWWW____W________WW____W___U____WW____W________WW____P________WW_____W___G___WW__U__W_W_____WW__________P__WW_______W_____WW___W___U_____WW___W_________WW___WWWWWW____WW_____P__W____WW_P______W____WWWWWWWWWWWWWWWW</value>\n" +
                    "        </entry>\n" +
                    "    </gameStateMap>\n" +
                    "</xmlDatabase>";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(XML_FILE))) {
                writer.write(xmlContent);
            }
        }
        underTest = XmlService.getInstance();
    }



    @Test
    public void testGetInstanceShouldReturnTheSameReferenceWhenCalled() {
        //Given - When
        XmlService secondInstance = XmlService.getInstance();
        //Then
        assertSame(underTest, secondInstance);
    }

    @Test
    public void testGetDatabaseShouldReturnADatabaseWhenCalled() {
        //Given
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(false);
        //When
        XmlDatabase actual = underTest.getDatabase();
        //Then
        assertNotNull(actual);
    }


    @Test
    public void testCreateXmlIfNotExistsShouldCreateAnXmlWhenItDoesNotExists() {
        //Given
        boolean deleted = XML_FILE.delete();
        //When
        underTest = XmlService.getInstance();
        XmlDatabase actual = underTest.getDatabase();
        //Then
        assertNotNull(actual);
    }

    @Test
    public void testSaveDatabase() throws Exception {
        //Given
        JAXBContext mockJaxbContext = mock(JAXBContext.class);
        Marshaller mockMarshaller = mock(Marshaller.class);
        when(mockJaxbContext.createMarshaller()).thenReturn(mockMarshaller);
        underTest.setJaxbContext(mockJaxbContext);
        //When
        underTest.saveDatabase();
        //Then
        verify(mockMarshaller, times(1)).marshal(any(XmlDatabase.class), any(File.class));
    }


}