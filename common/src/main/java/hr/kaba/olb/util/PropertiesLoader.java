package hr.kaba.olb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

//    private final static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    public static Properties load(String fileName) throws IOException {

//        logger.info("loading properties for {}", fileName);

        Properties properties = new Properties();

        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {

            properties.load(input);

        } catch (IOException e) {
//            logger.warn(e.getLocalizedMessage());
            throw e;
        }

        return properties;

    }

}
