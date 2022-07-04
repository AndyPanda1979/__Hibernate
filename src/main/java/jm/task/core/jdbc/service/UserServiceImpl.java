package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
    public void createUsersTable() throws SQLException {
        String rawSQL = """
                CREATE TABLE IF NOT EXISTS `test1`.`users` (
                  `id` BIGINT(64) NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastName` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL,
                  PRIMARY KEY (`id`));""";
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(rawSQL).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void dropUsersTable() throws SQLException {
        String rawSQL = "DROP TABLE IF EXISTS `test1`.`users`;";
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(rawSQL).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.flush();
        System.out.println("User с именем " + name + " " + lastName + " добавлен в БД");
        session.getTransaction().commit();
    }

    public void removeUserById(long id) throws SQLException {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        session.getTransaction().commit();
    }

    public List<User> getAllUsers() throws SQLException {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        List <User> users = session.createQuery("SELECT u FROM User u", User.class).getResultList();
        session.getTransaction().commit();
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        Query q3 = session.createQuery("DELETE FROM User");
        q3.executeUpdate();
        session.getTransaction().commit();
    }
}
