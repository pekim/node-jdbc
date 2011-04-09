package uk.co.pekim.nodejdbc;

import java.io.IOException;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.configuration.Configuration;
import uk.co.pekim.nodejdbc.notifynode.NotifyInitialised;
import uk.co.pekim.nodejdbc.notifynode.NotifyNode;
import uk.co.pekim.nodejdbc.server.Server;

/**
 * Node/JDBC bridge server.
 * 
 * @author Mike D Pilsbury
 */
public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    // private final ApplicationContext context;
    private final Configuration configuration;

    private Main(final String configurationJson) {
        this.configuration = Configuration.parseJson(configurationJson);
        // this.context = new ClassPathXmlApplicationContext(new String[] {
        // "/uk/co/pekim/nodejdbc/nodejdbc.xml" });
    }

    private void run() {
        LOGGER.info("Starting NodeJdbc server");

        // Object bean = context.getBean("testbean", TestBean.class);
        // LOGGER.info(bean.toString());

        try {
            Server server = new Server();
            server.start();

            NotifyNode notifyNode = new NotifyNode(configuration.getNode().getPort());
            notifyNode.send(new NotifyInitialised(server.getAddress().getPort()));

            synchronized (this) {
                wait();
            }
        } catch (IOException exception) {
            throw new NodeJdbcException("Server problem", exception);
        } catch (InterruptedException exception) {
            throw new NodeJdbcException("Server problem", exception);
        }
    }

    /**
     * Run the server.
     * 
     * @param args
     *            command arguments.
     */
    public static void main(final String[] args) {
        initialiseLogging();

        try {
            if (args.length < 1) {
                throw new NodeJdbcException("Expected 1 argument, a JSON string");
            }

            new Main(args[0]).run();
        } catch (NodeJdbcException exception) {
            LOGGER.error("Fatal error", exception);
            System.exit(1);
        }
    }

    private static void initialiseLogging() {
        DOMConfigurator.configure(Main.class.getResource("/uk/co/pekim/nodejdbc/log4j.xml"));
    }
}
