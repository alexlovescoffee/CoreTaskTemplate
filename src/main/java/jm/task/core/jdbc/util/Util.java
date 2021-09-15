package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    //JDBC
    private static final SqlProperties prop = SqlProperties.getInstance();

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(prop.get("driver"));
            return DriverManager.getConnection(prop.get("url"), prop.get("user"), prop.get("password"));
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    //Hibernate
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration cfg = new Configuration();
            cfg.setProperties(getHibernateProperties());
            cfg.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }

    private static Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, prop.get(Environment.DRIVER));
        properties.setProperty(Environment.URL, prop.get(Environment.URL));
        properties.setProperty(Environment.USER, prop.get(Environment.USER));
        properties.setProperty(Environment.PASS, prop.get(Environment.PASS));
        properties.setProperty(Environment.DIALECT, prop.get(Environment.DIALECT));
        properties.setProperty(Environment.SHOW_SQL, prop.get(Environment.SHOW_SQL));
        properties.setProperty(Environment.HBM2DDL_AUTO, prop.get(Environment.HBM2DDL_AUTO));
        return properties;
    }
}
