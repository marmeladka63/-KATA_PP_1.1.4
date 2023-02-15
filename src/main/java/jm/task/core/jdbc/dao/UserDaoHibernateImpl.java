package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFact = Util.getSession();
    public static User user;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFact.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users("
                    + "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY , "
                    + "name VARCHAR(255) NOT NULL, "
                    + "lastName VARCHAR(255) NOT NULL, "
                    + "age TINYINT UNSIGNED)").executeUpdate();

            session.getTransaction().commit();
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFact.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();

        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFact.getCurrentSession()) {
            user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }

    }


    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFact.getCurrentSession()) {
            user = new User();
            user.setId(id);
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }

    }


    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFact.getCurrentSession()) {
            session.beginTransaction();

            List<User> users = session.createQuery("from User").getResultList();
            for (User u : users) {
                System.out.println(u);
            }
            session.getTransaction().commit();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFact.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE  users").executeUpdate();
            session.getTransaction().commit();
        }

    }
}
