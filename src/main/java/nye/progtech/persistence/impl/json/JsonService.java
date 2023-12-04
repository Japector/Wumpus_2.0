package nye.progtech.persistence.impl.json;

import java.io.File;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import nye.progtech.exceptions.CouldNotReachDatabase;


public class JsonService implements JsonServiceInterface {


    private static final File JSON_FILE = new File("GameStateDatabase.json");
    private static JsonService instance;
    private static JsonDatabase database;

    private ObjectMapper mapper;

    private JsonService() {
        mapper = new ObjectMapper();
    }

    public static JsonService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new JsonService();
            createXmlIfNotExists();
        }
        return instance;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public JsonDatabase getDatabase() {
        return database;
    }

    public void saveDatabase() throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(JSON_FILE, database);
    }

    public static JsonDatabase loadDatabase() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON_FILE, JsonDatabase.class);
    }

    private static void createXmlIfNotExists() {
        if (JSON_FILE.exists()) {
            try {
                database = loadDatabase();
            } catch (Exception e) {
                throw new CouldNotReachDatabase("Could not reach database" + e.getMessage(), e);
            }
            database.updateNextId();
        } else {
            database = new JsonDatabase();
        }
    }
}
