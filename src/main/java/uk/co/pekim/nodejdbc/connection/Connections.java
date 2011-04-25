/**
 * 
 */
package uk.co.pekim.nodejdbc.connection;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JDBC connections, keyed by UUIDs (that are exposed to the Node callers).
 * 
 * @author Mike D Pilsbury
 */
public final class Connections {
    /**
     * Connections, keyed by UUIDs (that are exposed to the Node callers).
     */
    public static final Map<UUID, Connection> CONNECTIONS;

    static {
        CONNECTIONS = new ConcurrentHashMap<UUID, Connection>();
    }

    private Connections() {
    }
}
