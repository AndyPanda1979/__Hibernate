package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
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

    @Override
    public void dropUsersTable() {
        String rawSQL = "DROP TABLE IF EXISTS `test1`.`users`;";
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(rawSQL).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.flush();
        System.out.println("User с именем " + name + " " + lastName + " добавлен в БД");
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        List <User> users = session.createQuery("SELECT u FROM User u", User.class).getResultList();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getHibernateFactory().getCurrentSession();
        session.beginTransaction();
        Query q3 = session.createQuery("DELETE FROM User");
        q3.executeUpdate();
        session.getTransaction().commit();
    }
}
