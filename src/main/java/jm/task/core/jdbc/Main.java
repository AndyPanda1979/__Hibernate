package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {

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



//        System.out.println("===============  ТЕСТИРОВАНИЕ ===============");
//        UserServiceImpl userService = new UserServiceImpl();
//        System.out.println("Делаем таблицу");
//        userService.createUsersTable();
//
//        System.out.println("Вставляем двух пользователей");
//        userService.saveUser("Михаил", "Ломоносов", (byte) 33);
//        userService.saveUser("Дмитрий", "Менделеев", (byte) 51);
//
//        System.out.println("Берем всех пользователей");
//        for (User item : userService.getAllUsers() ) {
//            System.out.println(item);
//        }
//
//        System.out.println("Пробуем удалить по id 30");
//        userService.removeUserById(30);
//
//        System.out.println("Берем всех пользователей");
//        for (User item : userService.getAllUsers() ) {
//            System.out.println(item);
//        }
//
//        System.out.println("Пробуем удалить по id 1");
//        userService.removeUserById(1);
//
//        System.out.println("Берем всех пользователей");
//        for (User item : userService.getAllUsers() ) {
//            System.out.println(item);
//        }
//
//        System.out.println("Очищаем таблицу");
//        userService.cleanUsersTable();
//
//        System.out.println("Берем всех пользователей");
//        for (User item : userService.getAllUsers() ) {
//            System.out.println(item);
//        }
//
//        System.out.println("Удаляем таблицу");
//        userService.dropUsersTable();
//
//        System.out.println("Берем всех пользователей");
//        for (User item : userService.getAllUsers() ) {
//            System.out.println(item);
//        }
//
//        System.out.println("Пробуем удалить по id 1");
//        userService.removeUserById(1);
//
//        System.out.println("Пробуем сохранить запись");
//        userService.saveUser("Михаил", "Ломоносов", (byte) 33);
//
//        System.out.println("Очищаем таблицу");
//        userService.cleanUsersTable();
//
//        if (Util.getHibernateFactory() != null) {Util.getHibernateFactory().close();}
    }
}
