package nye.progtech.persistence.impl.jdbc;

import java.sql.Connection;

public interface DatabaseServiceInterface {
    Connection getConnection();
}
