/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata;

import java.util.ArrayList;
import java.util.List;

import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;

/**
 * Request metadata for a connection.
 * 
 * @author Mike D Pilsbury
 */
public class MetadataRequest implements NodeJavaRequest {
    private String connectionIdentifier;
    private final List<String> dataNames;

    /**
     * Create a metadata request.
     */
    public MetadataRequest() {
        dataNames = new ArrayList<String>();
    }

    /**
     * @return the connectionIdentifier
     */
    public String getConnectionIdentifier() {
        return connectionIdentifier;
    }

    /**
     * @param connectionIdentifier
     *            the connectionIdentifier to set
     */
    public void setConnectionIdentifier(final String connectionIdentifier) {
        this.connectionIdentifier = connectionIdentifier;
    }

    /**
     * @return the dataNames
     */
    public List<String> getDataNames() {
        return dataNames;
    }

    /**
     * @param dataName
     *            a dataName to add
     */
    public void addDataName(final String dataName) {
        dataNames.add(dataName);
    }

}
