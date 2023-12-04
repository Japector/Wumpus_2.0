package nye.progtech.persistence.impl.xml;

public interface XmlServiceInterface {
    XmlDatabase getDatabase();

    void saveDatabase() throws Exception;
}
