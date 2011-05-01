/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata;

import java.util.HashMap;
import java.util.Map;

import uk.co.pekim.nodejava.nodehandler.NodeJavaResponse;

/**
 * Requested meta data.
 * 
 * @author Mike D Pilsbury
 */
public class MetadataResponse implements NodeJavaResponse {
    private final Map<String, Object> data;

    /**
     * Create a metadata response.
     */
    public MetadataResponse() {
        data = new HashMap<String, Object>();
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void addData(final String name, final Object value) {
        data.put(name, value);
    }
}
