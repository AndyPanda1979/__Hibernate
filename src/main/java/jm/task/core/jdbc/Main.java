package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {

        //   ******   jdbc   ******   //
        UserDaoJDBCImpl jdbcService = new UserDaoJDBCImpl();
        jdbcService.createUsersTable();
        System.out.println("JDBC");
        jdbcService.saveUser("Юрий", "Гагарин", (byte) 35);
        jdbcService.saveUser("Пётр", "Великий", (byte) 37);
        jdbcService.saveUser("Валентина", "Терешкова", (byte) 30);
        jdbcService.saveUser("Александра", "Потанина", (byte) 32);
        for (User item: jdbcService.getAllUsers()) {
            System.out.println(item);
        }
        jdbcService.cleanUsersTable();
        jdbcService.dropUsersTable();

        //   ******   hibernate   ******   //
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        System.out.println("Hibernate");
        userService.saveUser("Михаил", "Ломоносов", (byte) 33);
        userService.saveUser("Дмитрий", "Менделеев", (byte) 51);
        userService.saveUser("Зинаида", "Ермольева", (byte) 40);
        userService.saveUser("Фатима", "Бутаева", (byte) 45);
        for (User item : userService.getAllUsers() ) {
            System.out.println(item);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();

        //    ******   CLose connections   ******   //
        if (Util.getConnection() != null) {Util.getConnection().close();}
        if (Util.getHibernateFactory() != null) {Util.getHibernateFactory().close();}
    }
}
