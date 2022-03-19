package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(45), lastName VARCHAR(45), age TINYINT(100));";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица users создана");
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users;";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица users удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("User с именем " + name + "_" + lastName + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("User с id " + id + " удалён из таблицы");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("Лист из Users создан");
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE users;";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица users очищена");
    }
}
