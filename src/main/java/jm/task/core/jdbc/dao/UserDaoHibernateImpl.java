package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlProperties;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static final SqlProperties SQL = SqlProperties.getInstance();
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        if (isTableExists())
            logger.log(Level.INFO, "Таблица {0} уже существует!", SQL.get("table"));
        else {
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.createSQLQuery(SQL.get("create_users_table")).executeUpdate();
            tr.commit();
            session.close();
            logger.log(Level.INFO, "Таблица {0} создана!", SQL.get("table"));
        }
    }

    @Override
    public void dropUsersTable() {
        if (!isTableExists())
            logger.log(Level.INFO, "Таблица {0} не существует!", SQL.get("table"));
        else {
            Session session = sessionFactory.openSession();
            Transaction tr = session.beginTransaction();
            session.createSQLQuery(SQL.get("drop_users_table")).executeUpdate();
            tr.commit();
            session.close();
            logger.log(Level.INFO, "Таблица {0} удалена!", SQL.get("table"));
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tr.commit();
        session.close();
        logger.log(Level.INFO, "Пользователь {0} {1} добавлен!", new String[]{name, lastName});
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        if (session.createQuery(SQL.get("remove_user_by_id_hql")).setParameter("id", id).executeUpdate() == 1)
            logger.log(Level.INFO, "Пользователь с id={0} удален!", id);
        tr.commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return (List<User>) sessionFactory.openSession().createQuery(SQL.get("get_all_users_hql")).list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        int deletedRows = session.createQuery(SQL.get("clean_users_table_hql")).executeUpdate();
        if (deletedRows > 0)
            logger.log(Level.INFO, "Таблица {0} очищена! Всего удалено {1} пользователей", new Object[]{SQL.get("table"), deletedRows});
        tr.commit();
        session.close();
    }

    private boolean isTableExists() {
        return sessionFactory.openSession()
                .createSQLQuery(SQL.get("is_table_exists")).list().size() != 0;
    }
}
