/**
 * 
 */
package uk.co.pekim.nodejdbc;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Notify the parent Node process of various events.
 *
 * @author Mike D Pilsbury
 */
public class NotifyNode {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private OutputStreamWriter output;

    public void connect(final int port) {
        try {
            final Socket socket = new Socket(InetAddress.getLocalHost(), port);
            output = new OutputStreamWriter(socket.getOutputStream());
        } catch (UnknownHostException exception) {
            throw new NodeJdbcException("Failed to connect to node", exception);
        } catch (IOException exception) {
            throw new NodeJdbcException("Failed to connect to node", exception);
        }
    }
    
    public void send(final Object message) {
        try {
            final String jsonString = jsonMapper.writeValueAsString(message);
            final String netString = "" + jsonString.length() + ":" + jsonString + ",";
            
            output.write(netString);
            output.flush();
        } catch (JsonGenerationException exception) {
            throw new NodeJdbcException("Failed to create JSON from " + message, exception);
        } catch (JsonMappingException exception) {
            throw new NodeJdbcException("Failed to create JSON from " + message, exception);
        } catch (IOException exception) {
            throw new NodeJdbcException("Failed to send JSON for " + message, exception);
        }
    }
}
