package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getConnection().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users " +
                    "(ID INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR (40), " +
                    "lastname VARCHAR(40), age INT (3))").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица создана");
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getConnection().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS mytest.User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (org.hibernate.Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> Users = new ArrayList<>();
        try (Session session = Util.getConnection().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            Transaction transaction = session.beginTransaction();
            Users = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}