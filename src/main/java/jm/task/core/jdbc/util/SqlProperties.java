package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class SqlProperties {
    private static final String PROP_FILE_NAME = "SQL.properties";
    private static final SqlProperties instance = new SqlProperties();

    public static SqlProperties getInstance() {
        return instance;
    }

    private Properties properties;

    private SqlProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME)) {
            properties = new Properties();
            properties.load(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
