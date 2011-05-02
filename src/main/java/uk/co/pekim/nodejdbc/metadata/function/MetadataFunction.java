/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata.function;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Function exposing metadata.
 * 
 * @author Mike D Pilsbury
 * @param <T>
 *            the type of the metadata.
 */
public interface MetadataFunction<T> {
    /**
     * Execute the function that gets the metatdata.
     * 
     * @param metaData
     *            a Connection's metadata.
     * @return the required metadata.
     * @throws SQLException
     *             if something went wrong.
     */
    T execute(DatabaseMetaData metaData) throws SQLException;
}
