package dev.modichamiya.eclipse.database;

import java.sql.*;
import java.util.List;

public final class MigrationRunner {
    private record Migration(int version, List<String> statements) { }
    private static final List<Migration> MIGRATIONS = List.of(new Migration(1, List.of(
        "CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, profile_json TEXT NOT NULL, updated_at INTEGER NOT NULL)",
        "CREATE INDEX IF NOT EXISTS idx_players_updated_at ON players(updated_at)"
    )));

    public void migrate(DatabaseProvider provider) throws SQLException {
        try (Connection connection = provider.connection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) { statement.executeUpdate("CREATE TABLE IF NOT EXISTS schema_version (version INTEGER PRIMARY KEY, applied_at INTEGER NOT NULL)"); }
            int current = 0; try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT COALESCE(MAX(version), 0) FROM schema_version")) { if (rs.next()) current = rs.getInt(1); }
            try {
                for (Migration migration : MIGRATIONS) if (migration.version > current) {
                    try (Statement statement = connection.createStatement()) { for (String sql : migration.statements) statement.executeUpdate(sql); }
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO schema_version(version, applied_at) VALUES (?, ?)")) { ps.setInt(1, migration.version); ps.setLong(2, System.currentTimeMillis()); ps.executeUpdate(); }
                }
                connection.commit();
            } catch (SQLException failure) { connection.rollback(); throw failure; }
            finally { connection.setAutoCommit(true); }
        }
    }
}
