package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dao.UserDao;
import sample.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDao userDao;
    private ObservableList<User> allUsers;

    public UserService() throws SQLException {
        userDao = new UserDao();
        allUsers = FXCollections.observableArrayList(userDao.getUserList());
    }

    public int isUser(String login, String password) {
        String user_type = "";
        User user;
        for (User value : allUsers) {
            user = value;
            if (user.getLogin().equals(login)
                    && user.getPassword().equals(password)) {
                user_type = user.getType();
                System.out.println(user_type);
                break;
            }
        }
        return switch (user_type) {
            case "admin" -> 0;
            case "staff" -> 1;
            case "members" -> 2;
            default -> 5;
        };
    }

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }


    public void updateType(int id, String newType) {
        try {
            userDao.updateType(id, newType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLogin(int id, String newLogin)  {
        try {
            userDao. updateLogin(id,newLogin);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updatePassword(int id, String newPass)  {
        try {
            userDao. updateLogin(id,newPass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(Integer id) {
        try {
            userDao.delete(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
