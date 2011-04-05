package uk.co.pekim.nodejdbc;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 * 
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        DOMConfigurator.configure(App.class.getResource("/uk/co/pekim/nodejdbc/log4j.xml"));
        
        LOGGER.info("Starting NodeJdbc server");

        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "/uk/co/pekim/nodejdbc/nodejdbc.xml" });

        Object bean = context.getBean("testbean", TestBean.class);
        LOGGER.info(bean.toString());
    }
}
