package uk.co.pekim.nodejdbc;

import java.io.IOException;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.pekim.nodejdbc.configuration.Configuration;
import uk.co.pekim.nodejdbc.server.Server;

/**
 * Node/JDBC bridge server.
 * 
 * @author Mike D Pilsbury
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private final ApplicationContext context;
    private final Configuration configuration;

    private Main(String configurationJson) {
        this.configuration = Configuration.parseJson(configurationJson);
        this.context = new ClassPathXmlApplicationContext(new String[] { "/uk/co/pekim/nodejdbc/nodejdbc.xml" });
    }

    private void run() {
        LOGGER.info("Starting NodeJdbc server");
        
        Object bean = context.getBean("testbean", TestBean.class);
        LOGGER.info(bean.toString());
        
        try {
            new Server().run();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
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
