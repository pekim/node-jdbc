/**
 * 
 */
package uk.co.pekim.nodejdbc.notifynode;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.pekim.nodejdbc.NodeJdbcException;
import uk.co.pekim.nodejdbc.netstring.Netstring;

/**
 * Notify the parent Node process of various events.
 * 
 * @author Mike D Pilsbury
 */
public class NodeNotifier {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private OutputStreamWriter output;

    /**
     * Create a Node notifier.
     * 
     * @param port
     *            the port that a Node instance is listening on.
     */
    public NodeNotifier(final int port) {
        try {
            final Socket socket = new Socket(InetAddress.getLocalHost(), port);
            output = new OutputStreamWriter(socket.getOutputStream());
        } catch (UnknownHostException exception) {
            throw new NodeJdbcException("Failed to connect to node", exception);
        } catch (IOException exception) {
            throw new NodeJdbcException("Failed to connect to node", exception);
        }
    }

    /**
     * Send a message to the Node instance. The message is send as a JSON
     * representation of the message, wrapped in a netstring.
     * 
     * @param message
     *            the message to send.
     */
    public void send(final Object message) {
        try {
            final String jsonString = JSON_MAPPER.writeValueAsString(message);
            final String netString = Netstring.build(jsonString);

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
