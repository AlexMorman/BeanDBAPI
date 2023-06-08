package ServerControllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SpringBootApplication
public class Application
{
    public final static Logger LOGGER = Logger.getLogger("");

    public static void main(String[] args) throws Exception
    {
        // load properties from Parameters.config
        Properties properties = new Properties();
        properties.load(new FileInputStream("Parameters.config"));

        LOGGER.info("Starting Logger");

        // create log file and set file handler
        FileHandler fileHandler = new FileHandler(properties.getProperty("LOG_PATH"));
        SimpleFormatter simple = new SimpleFormatter();
        fileHandler.setFormatter(simple);
        LOGGER.addHandler(fileHandler);

        LOGGER.info("Starting server");

        SpringApplication application = new SpringApplication(Application.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", properties.get("PORT")));
        application.run(args);

        LOGGER.info("Started server on port: " + properties.get("PORT"));
    }
}
