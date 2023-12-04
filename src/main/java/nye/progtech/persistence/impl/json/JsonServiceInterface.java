package nye.progtech.persistence.impl.json;


public interface JsonServiceInterface {
    JsonDatabase getDatabase();

    void saveDatabase() throws Exception;

}
