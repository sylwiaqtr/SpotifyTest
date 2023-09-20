package com.spotify.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class LoadCredentials {


    public static String loadProperty (String propertyName) {
        FileReader reader = null;
        try {
            reader = new FileReader("src/test/resources/client.properties");
            Properties properties = new Properties();
            properties.load(reader);
            return properties.getProperty(propertyName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
