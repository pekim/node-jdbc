package uk.co.pekim.nodejdbc;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.pekim.nodejdbc.configuration.Configuration;

/**
 * Node/JDBC bridge server.
 * 
 * @author Mike D Pilsbury
 */
public class NodeJdbc {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeJdbc.class);

    private final ApplicationContext context;
    private final Configuration configuration;

    private NodeJdbc(String configurationJson) {
        this.configuration = Configuration.parseJson(configurationJson);
        this.context = new ClassPathXmlApplicationContext(new String[] { "/uk/co/pekim/nodejdbc/nodejdbc.xml" });
    }

    private void run() {
        LOGGER.info("Starting NodeJdbc server");
        
        Object bean = context.getBean("testbean", TestBean.class);
        LOGGER.info(bean.toString());
    }

    public static void main(String[] args) {
        initialiseLogging();

        try {
            if (args.length < 1) {
                throw new NodeJdbcException("Expected 1 argument, a JSON string");
            }

            new NodeJdbc(args[0]).run();
        } catch (NodeJdbcException exception) {
            LOGGER.error("Fatal error", exception);
            throw exception;
        }
    }

    private static void initialiseLogging() {
        DOMConfigurator.configure(NodeJdbc.class.getResource("/uk/co/pekim/nodejdbc/log4j.xml"));
    }
}
