package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.controllers.UsersHistoryController;
import sample.dao.UserHistoryDao;
import sample.model.UsersHistory;

import java.sql.SQLException;


public class UserHistoryService {
    private UserHistoryDao dao;
    private ObservableList<UsersHistory> allHistory;

    public UserHistoryService()  {
        try {
            dao = new UserHistoryDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        allHistory = FXCollections.observableArrayList(dao.getUserHistoryList());
    }
    public void clear()  {
        try {
             dao.clear();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public int create(int userId, String descr)  {
        try {
            return dao.create(userId, descr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public ObservableList<UsersHistory> getAllHistory() {
        return allHistory;
    }

}
