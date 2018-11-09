package hr.kaba.olb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading properties from a file
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   15.07.2018
 */
public class PropertiesLoader {
    /**
     *
     * @param fileName file containing name value pairs
     * @return Properties
     * @throws IOException when file resource is not available
     */
    public static Properties load(String fileName) throws IOException {
        Properties properties = new Properties();

        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            throw e;
        }

        return properties;

    }

}
