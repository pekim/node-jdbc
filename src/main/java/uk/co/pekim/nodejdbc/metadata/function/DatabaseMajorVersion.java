/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata.function;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Expose metadata.
 * 
 * @author Mike D Pilsbury
 */
public final class DatabaseMajorVersion implements MetadataFunction<Integer> {
    @Override
    public Integer execute(final DatabaseMetaData metaData) throws SQLException {
        return metaData.getDatabaseMajorVersion();
    }
}
