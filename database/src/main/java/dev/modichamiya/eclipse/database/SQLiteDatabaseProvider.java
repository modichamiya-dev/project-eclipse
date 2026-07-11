package dev.modichamiya.eclipse.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.file.Path;
import java.sql.*;
import java.util.Objects;

public final class SQLiteDatabaseProvider implements DatabaseProvider {
    private final Path file; private final int poolSize; private final long timeoutMs; private HikariDataSource dataSource;
    public SQLiteDatabaseProvider(Path file, int poolSize, long timeoutMs) { this.file = Objects.requireNonNull(file); this.poolSize = Math.max(1, poolSize); this.timeoutMs = Math.max(250, timeoutMs); }
    public void start() throws SQLException {
        HikariConfig config = new HikariConfig(); config.setPoolName("Eclipse-SQLite"); config.setJdbcUrl("jdbc:sqlite:" + file.toAbsolutePath());
        config.setDriverClassName("org.sqlite.JDBC"); config.setMaximumPoolSize(poolSize); config.setConnectionTimeout(timeoutMs); config.setAutoCommit(true);
        config.setConnectionInitSql("PRAGMA foreign_keys=ON; PRAGMA journal_mode=WAL; PRAGMA busy_timeout=5000;"); dataSource = new HikariDataSource(config);
        try (Connection ignored = connection()) { }
    }
    public Connection connection() throws SQLException { if (dataSource == null) throw new SQLException("Database is not started"); return dataSource.getConnection(); }
    public void close() { if (dataSource != null) { dataSource.close(); dataSource = null; } }
}
