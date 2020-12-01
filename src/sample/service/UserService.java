package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.UserDao;
import sample.model.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;
    private ObservableList<User> allUsers;

    public UserService() throws SQLException {
        userDao = new UserDao();
        allUsers = FXCollections.observableArrayList(userDao.getUserList());
    }


    public User isUser(String login, String password) {
        for (int i=0; i< allUsers.size(); i++) {
            if (allUsers.get(i).getLogin().equals(login)
                    && allUsers.get(i).getPassword().equals(password)) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    public boolean isEmail(String email) {
        return userDao.isEmail(email);
    }

    public int create(String login, String passw, String userType, int user_num)  {
        try {
            return userDao.create(login, passw, userType, user_num);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
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
            userDao. updatePassword(id,newPass);
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
