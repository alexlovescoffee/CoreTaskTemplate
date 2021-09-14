package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final SqlProperties prop = SqlProperties.getInstance();

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(prop.get("driver"));
            return DriverManager.getConnection(prop.get("url"), prop.get("user"), prop.get("password"));
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }
}
