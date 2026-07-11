package dev.modichamiya.eclipse.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseProvider extends AutoCloseable {
    void start() throws SQLException;
    Connection connection() throws SQLException;
    @Override void close();
}
