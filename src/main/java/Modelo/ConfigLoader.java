package Modelo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader(String path) {
        properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            if (inputStream == null) {
                throw new IOException("El archivo de configuración no se encuentra en el classpath: " + path);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de configuración: " + path, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
