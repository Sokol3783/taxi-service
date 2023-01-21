package org.example.dao.daoimplementaion;

import org.example.dao.AbstractDAO;
import org.example.dao.DAOUser;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.User;
import org.example.models.taxienum.UserRole;
import org.example.security.PasswordAuthentication;
import org.example.util.LocalDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.exceptions.DAOException.*;

public class UserDAOimpl extends AbstractDAO<User> implements DAOUser<User> {
    private static final Logger log = LoggerFactory.getLogger(UserDAOimpl.class);
    private static final String CREATE = "INSERT INTO users(first_name,last_name,phone,user_role,email,birthday,password) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET (first_name=?,last_name=?,phone=?,email=?,birthday=?) WHERE user_id=?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE phone=? AND email=?";
    private static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE user_id=?";
    private static final String SELECT_BY_PHONEMAIL = "SELECT * FROM users WHERE (phone=? OR email=?)";

    private static SimpleConnectionPool pool;

    private UserDAOimpl() {
    }

    public static UserDAOimpl getInstance() {
        synchronized (UserDAOimpl.class) {
            if (pool == null) {
                pool = BasicConnectionPool.getInstance();
            }
        }
        return new UserDAOimpl();
    }

    @Override
    public User create(User model) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = getPrepareStatementCreateUser(model, CREATE, con);
            User user = executeCreateUpdateQuery(statement);
            commitUserTransaction(user, con);
            return user;
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        return User.builder().build();
    }

    @Override
    public User create(User model, String password) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = getPrepareStatementCreateUser(model, CREATE, con);
            fillUserPassword(statement, password);
            User user = executeCreateUpdateQuery(statement);
            commitUserTransaction(user, con);
            return user;
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(AUTO_COMMIT_FALSE_FAILED, e);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        return null;
    }

    private void commitUserTransaction(User user, Connection con) throws SQLException {
        if (!user.isEmpty()) {
            con.commit();
        }
    }

    private User executeCreateUpdateQuery(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return buildUser(result);
        }
        return User.builder().build();
    }

    private PreparedStatement getPrepareStatementCreateUser(User model, String query, Connection con) {
        try (PreparedStatement statement = getPrepareStatement(model, query, con)) {
            return statement;
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(USER_NOT_CREATE, e);
            throw new DAOException();
        }
    }

    private PreparedStatement getPrepareStatement(User model, String query, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(query);
        fillFieldsExceptPassword(statement, model);
        return statement;
    }

    private void fillFieldsExceptPassword(PreparedStatement statement, User model) throws SQLException {
        statement.setString(1, model.getFirstName());
        statement.setString(2, model.getSecondName());
        statement.setString(3, model.getPhone());
        statement.setString(4, String.valueOf(model.getRole()));
        statement.setString(5, model.getEmail());
        statement.setDate(6, Date.valueOf(model.getBirthDate()));
    }

    private void fillUserPassword(PreparedStatement statement, String password) throws SQLException {
        PasswordAuthentication auth = new PasswordAuthentication(13);
        statement.setString(7, auth.hash(password.toCharArray()));
    }

    @Override
    public void update(User model) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = getPrepareStatementUpdateUser(model, UPDATE, con);
            executeCreateUpdateQuery(statement);
            commitUpdateTransaction(statement.getUpdateCount(), con);
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(AUTO_COMMIT_FALSE_FAILED, e);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }

    private PreparedStatement getPrepareStatementUpdateUser(User model, String query, Connection con) {
        try (PreparedStatement statement = getPrepareStatement(model, query, con)) {
            return statement;
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(USER_NOT_UPDATE, e);
            throw new DAOException();
        }
    }


    @Override
    public User get(int id) {
        Connection con = pool.getConnection();
        try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return buildUser(result);
            }
        } catch (SQLException e) {
            log.error(USER_NOT_FOUND, e);
            throw new DAOException(USER_NOT_FOUND);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        return User.builder().build();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        Connection con = pool.getConnection();
        try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(buildUser(result));
            }
        } catch (SQLException e) {
            log.error(USER_NOT_FOUND, e);
            throw new DAOException(USER_NOT_FOUND);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        return users;
    }

    @Override
    public void delete(int id) {
        //OrderDAO.callQuery(id, con, DELETE, log);
    }

    @Override
    public User get(String login) {
        return null;
    }

    @Override
    public User findUserPhoneMailAndPassword(String login, String password) {
        Connection con = pool.getConnection();
        try (PreparedStatement statement = con.prepareStatement(SELECT_BY_PHONEMAIL)) {
            PasswordAuthentication auth = new PasswordAuthentication(13);
            statement.setString(1, login);
            statement.setString(2, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (auth.authenticate(password.toCharArray(), resultSet.getString("password"))) {
                    return buildUser(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            log.error(USER_NOT_FOUND, e);
            throw new DAOException(USER_NOT_FOUND);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }


    @Override
    public void updatePassword(User model, String newPassword) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            try (PreparedStatement statement = con.prepareStatement(UPDATE_PASSWORD)) {
                fillUserPassword(statement, newPassword);
                statement.setString(2, model.getEmail());
                statement.setString(3, model.getPhone());
                commitUpdateTransaction(statement.executeUpdate(), con);

            }
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(USER_NOT_FOUND, e);
            throw new DAOException(USER_NOT_FOUND);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }

    private void commitUpdateTransaction(int executeUpdate, Connection con) throws SQLException {
        if (executeUpdate > 1) {
            con.commit();
        } else {
            String message = USER_NOT_UPDATE;
            log.error(message);
            throw new DAOException(message);
        }
    }


    //TODO
    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder().firstName(resultSet.getString("first_name"))
                .secondName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .role(UserRole.getRole(resultSet.getString("user_role")))
                .birthDate(LocalDateConverter.convertToEntityAttribute(resultSet.getDate("birthday")))
                .build();
    }
}
