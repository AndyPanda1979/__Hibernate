package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.*;


public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Михаил", "Ломоносов", (byte) 33);
        userService.saveUser("Дмитрий", "Менделеев", (byte) 51);
        userService.saveUser("Зинаида", "Ермольева", (byte) 40);
        userService.saveUser("Фатима", "Бутаева", (byte) 45);
        for (User item : userService.getAllUsers() ) {
            System.out.println(item);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();

        if (Util.getHibernateFactory() != null) {Util.getHibernateFactory().close();}
    }
}
