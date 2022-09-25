package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    //private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable(){
        String command1 = "CREATE TABLE IF NOT EXISTS Users (ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR (40), " +
                "lastname VARCHAR(40), " +
                "age INT (3))";
        try (Connection connection= (Connection) getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(command1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String command2 = "DROP TABLE IF EXISTS Users";
        try (Connection connection= (Connection) getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(command2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command3 = "INSERT INTO Users(name, lastname, age) VALUES(?,?,?)";
        try (Connection connection= (Connection) getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command3)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String command4 = "DELETE FROM users WHERE id";
        try (Connection connection= (Connection) getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(command4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from users";

        try (Connection connection= (Connection) getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUser;
    }

    public void cleanUsersTable() {
        String command6 = "DELETE FROM users";
        try (Connection connection= (Connection) getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(command6);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
