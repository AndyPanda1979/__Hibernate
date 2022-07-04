package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.jaxb.cfg.spi.JaxbCfgHibernateConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.internal.SessionFactoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static SessionFactory sessionFactory;

    //  ********  hibernate  ********  //
    public static SessionFactory getHibernateFactory () {
        try {
            if (sessionFactory == null) {
                Properties connectProperties = new Properties();
                connectProperties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
                connectProperties.put(Environment.URL,"jdbc:mysql://localhost/test1");
                connectProperties.put(Environment.USER,"root");
                connectProperties.put(Environment.PASS,"admin123");
//                connectProperties.put(Environment.SHOW_SQL,"true");
                connectProperties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
                connectProperties.put(Environment.HBM2DDL_AUTO, "none");
                Configuration configuration = new Configuration();
                configuration.setProperties(connectProperties);
                configuration.addAnnotatedClass(User.class);
                return sessionFactory = configuration.buildSessionFactory();
            }
            return sessionFactory;
        } catch (Exception e) {
            System.out.println("При создании соединения Hibernate произошла ошибка");
            e.printStackTrace();
        }
        return null;
    }

    //  ********  jdbc  ********  //
    private static Connection connection;
    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/test1", "root", "admin123");
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("При создании соединения JDBC произошла ошибка");
//            e.printStackTrace();
        }
        return null;
    }

}