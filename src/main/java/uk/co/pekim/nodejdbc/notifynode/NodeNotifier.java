/**
 * 
 */
package uk.co.pekim.nodejdbc.notifynode;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.NodeJdbcException;
import uk.co.pekim.nodejdbc.netstring.Netstring;

/**
 * Notify the parent Node process of various events.
 * 
 * @author Mike D Pilsbury
 */
public class NodeNotifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeNotifier.class);
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final int ALIVE_PERIOD = 1000;

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

            initAlive();
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
            LOGGER.error("Shutting down because node server no longer available", exception);

            // TODO Implement a much cleaner shutdown.
            System.exit(1);
        }
    }

    private void initAlive() {
        final NotifyMessage message = new AliveMessage();
        Timer timer = new Timer("node-alive", true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                send(message);
            }
        }, ALIVE_PERIOD, ALIVE_PERIOD);
    }

    private final class AliveMessage extends NotifyMessage {
        private AliveMessage() {
            super("alive");
        }
    }
}
