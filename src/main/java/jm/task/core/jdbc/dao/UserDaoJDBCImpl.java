package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String rawSQL = """
                CREATE TABLE IF NOT EXISTS `test1`.`users` (
                  `id` BIGINT(64) NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastName` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL,
                  PRIMARY KEY (`id`));""";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(rawSQL);
        }
    }

    public void dropUsersTable() throws SQLException {
        String rawSQL = "DROP TABLE IF EXISTS `test1`.`users`;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(rawSQL);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String rawSQL = "INSERT INTO `test1`.`users` (`name`, `lastName`, `age`) VALUES ('" + name + "', '" + lastName + "', '" + age + "');";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(rawSQL);
            System.out.println("User с именем " + name + " " + lastName + " добавлен в БД");
        }
    }

    public void removeUserById(long id) throws SQLException {
        String rawSQL = "DELETE FROM `users` WHERE `id`=" + id + ";";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(rawSQL);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String rawSQL = "SELECT * FROM test1.users;";
        List <User> users = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(rawSQL);
            while (result.next()) {
                User currentUser;
                users.add(currentUser = new User(result.getString("name"), result.getString("lastName"), result.getByte("age")));
                currentUser.setId(result.getLong("id"));
            }
        }
            return users;
    }

    public void cleanUsersTable() throws SQLException {
        String rawSQL = "TRUNCATE TABLE `users`;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(rawSQL);
        }
    }
}
