package nye.progtech.persistence.impl.xml;

import java.io.File;
import java.util.Objects;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import nye.progtech.exceptions.CouldNotReachDatabase;


public class XmlService implements XmlServiceInterface {

    private static final File XML_FILE = new File("GameStateDatabase.xml");
    private static XmlService instance;
    private static XmlDatabase database;
    private JAXBContext jaxbContext;

    private XmlService() {
        try {
            jaxbContext = JAXBContext.newInstance(XmlDatabase.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to create JAXBContext");
        }
    }

    public static XmlService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new XmlService();
            createXmlIfNotExists();
        }
        return instance;
    }

    public void setJaxbContext(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;
    }

    @Override
    public XmlDatabase getDatabase() {
        return database;
    }

    public void saveDatabase() throws Exception {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(database, XML_FILE);
    }

    public static XmlDatabase loadDatabase() throws Exception {
        JAXBContext context = JAXBContext.newInstance(XmlDatabase.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (XmlDatabase) unmarshaller.unmarshal(XML_FILE);
    }

    private static void createXmlIfNotExists() {
        if (XML_FILE.exists()) {
            try {
                database = loadDatabase();
            } catch (Exception e) {
                throw new CouldNotReachDatabase("Could not reach database" + e.getMessage(), e);
            }
            database.updateNextId();
        } else {
            database = new XmlDatabase();
        }
    }

}
