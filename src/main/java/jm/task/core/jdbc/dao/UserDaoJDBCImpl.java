package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlProperties;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static final SqlProperties SQL = SqlProperties.getInstance();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection con = Util.getConnection()) {
            if (!con.getMetaData().getTables(null, null, "users", new String[]{"TABLE"}).next()) {
                con.prepareStatement(SQL.get("create_users_table")).executeUpdate();
                logger.log(Level.INFO, "Таблица {0} создана!", SQL.get("table"));
            } else {
                logger.log(Level.INFO, "Таблица {0} уже существует!", SQL.get("table"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось создать таблицу!", e);
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection()) {
            if (con.getMetaData().getTables(null, null, "users", new String[]{"TABLE"}).next()) {
                con.prepareStatement(SQL.get("drop_users_table")).executeUpdate();
                logger.log(Level.INFO, "Таблица {0} удалена!", SQL.get("table"));
            } else {
                logger.log(Level.INFO, "Таблица {0} не существует!", SQL.get("table"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось удалить таблицу!", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnection()){
            PreparedStatement ps = con.prepareStatement(SQL.get("save_user"));
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            logger.log(Level.INFO, "Пользователь {0} {1} добавлен!", new String[]{name, lastName});
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось добавить пользователя!", e);
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SQL.get("remove_user_by_id"));
            ps.setLong(1, id);
            if (ps.executeUpdate() == 1)
                logger.log(Level.INFO, "Пользователь с id={0} удален!", id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось удалить пользователя!", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection con = Util.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SQL.get("get_all_users"));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new User(
                        rs.getString("NAME"),
                        rs.getString("LASTNAME"),
                        rs.getByte("AGE")));
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось получить список пользователей!", e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SQL.get("clean_users_table"));
            int deletedRows = ps.executeUpdate();
            if (deletedRows > 0)
                logger.log(Level.INFO, "Таблица {0} очищена! Всего удалено {1} пользователей", new Object[]{SQL.get("table"), deletedRows});
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось очистить таблицу!", e);
        }
    }
}
