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
public final class DoesMaxRowSizeIncludeBlobs implements MetadataFunction<Boolean> {
    @Override
    public Boolean execute(final DatabaseMetaData metaData) throws SQLException {
        return metaData.doesMaxRowSizeIncludeBlobs();
    }
}
