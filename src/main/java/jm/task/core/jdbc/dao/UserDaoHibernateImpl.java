package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFact = Util.getSession();
    private static Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

            try (Session session = sessionFact.openSession()) {
            transaction = session.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS users("
                    + "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY , "
                    + "name VARCHAR(255) NOT NULL, "
                    + "lastName VARCHAR(255) NOT NULL, "
                    + "age TINYINT UNSIGNED)";

            Query query = session.createSQLQuery(SQL);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();


        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFact.openSession()) {
            transaction = session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS users";
            Query query =  session.createSQLQuery(SQL);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFact.openSession()) {
           transaction= session.beginTransaction();
           session.save(new User(name,lastName,age));
           transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }


    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFact.openSession()) {
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    @Override
    public List<User> getAllUsers() {

            try (Session session = sessionFact.openSession()) {
                TypedQuery<User> query= session.createQuery("FROM User");
                List <User> users =  query.getResultList();
                return  users;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFact.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();


        }
    }
}
