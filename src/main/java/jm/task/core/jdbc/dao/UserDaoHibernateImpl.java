package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.FlushModeType;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
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

        try {
            Session session = Util.getHibernateFactory().getCurrentSession();
            session.setFlushMode(FlushModeType.COMMIT);
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(rawSQL).addEntity(User.class);
            try {
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При выполнении операции по созданию таблицы произошла ошибка, откат операции");
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (NullPointerException npe) {
            System.out.println("Операция не выполнена, не удалось подключиться к БД");
        }

    }

    @Override
    public void dropUsersTable() {
        try {
            String rawSQL = "DROP TABLE IF EXISTS `test1`.`users`;";
            Session session = Util.getHibernateFactory().getCurrentSession();
            session.setFlushMode(FlushModeType.COMMIT);
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(rawSQL).addEntity(User.class);
            try {
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При выполнении операции по удалению таблицы произошла ошибка, откат операции");
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (NullPointerException npe) {
            System.out.println("Операция не выполнена, не удалось подключиться к БД");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getHibernateFactory().getCurrentSession();
            session.setFlushMode(FlushModeType.COMMIT);
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                transaction.commit();
                System.out.println("User с именем " + name + " " + lastName + " добавлен в БД");
            } catch (Exception e) {
                System.out.println("При выполнении операции произошла ошибка, сохранение не выполнено, откат действий");
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (NullPointerException npe) {
            System.out.println("Операция не выполнена, не удалось подключиться к БД");
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            boolean checkUser = false;
            Session session = Util.getHibernateFactory().getCurrentSession();
            session.setFlushMode(FlushModeType.COMMIT);
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    checkUser = true;
                    session.delete(user);
                } else {
                    System.out.println("Не найден пользователь с id" + id);
                }
                transaction.commit();
                if (checkUser) {System.out.println("Удален пользователь c id " + id);}
            } catch (Exception e) {
                System.out.println("При выполнении операции произошла ошибка, удаление не выполнено, откат действий (Удаление пользователя)");
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (NullPointerException npe) {
            System.out.println("Операция не выполнена, не удалось подключиться к БД");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = Util.getHibernateFactory().getCurrentSession();
            session.setFlushMode(FlushModeType.COMMIT);
            Transaction transaction = session.beginTransaction();
            try {
                List <User> users = session.createQuery("SELECT u FROM User u", User.class).getResultList();
                transaction.commit();
                return users;
            } catch (Exception e) {
                System.out.println("При выполнении операции произошла ошибка, данные не были получены");
                transaction.rollback();
                e.printStackTrace();
                return new ArrayList<>();
            }
        } catch (NullPointerException npe) {
            System.out.println("Операция не выполнена, не удалось подключиться к БД");
        }
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getHibernateFactory().getCurrentSession();
            session.setFlushMode(FlushModeType.COMMIT);
            Transaction transaction = session.beginTransaction();
            Query q3 = session.createQuery("DELETE FROM User");
            q3.executeUpdate();
            try {
                transaction.commit();
            } catch (Exception e) {
                System.out.println("При выполнении операции произошла ошибка, откат действий (Очистка таблицы)");
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (NullPointerException npe) {
            System.out.println("Операция не выполнена, не удалось подключиться к БД");
        }
    }
}
