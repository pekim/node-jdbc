/**
 * 
 */
package uk.co.pekim.nodejdbc.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOServerConnection;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Mike D Pilsbury
 * 
 */
public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final String HOST = "localhost";

    private InetSocketAddress address;

    /**
     * Run the server.
     * 
     * @throws IOException
     *             exception
     */
    public void start() throws IOException {
        FilterChainBuilder filterChainBuilder = FilterChainBuilder.stateless();

        filterChainBuilder.add(new TransportFilter());
        filterChainBuilder.add(new NetStringFilter());
        filterChainBuilder.add(new JsonFilter());
        filterChainBuilder.add(new ProcessRequestFilter());

        final TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance().build();

        transport.setProcessor(filterChainBuilder.build());

        address = new InetSocketAddress(HOST, 0);
        TCPNIOServerConnection serverConnection = transport.bind(0);
        address = (InetSocketAddress) serverConnection.getLocalAddress();

        LOGGER.info("Server bound to port " + address.getPort());

        transport.start();
    }

    /**
     * The address the server is bound to.
     * 
     * @return the address
     */
    public InetSocketAddress getAddress() {
        return address;
    }
}
