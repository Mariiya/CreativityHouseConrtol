package sample.service;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.controllers.main.ScreenController;
import sample.dao.GroupDao;
import sample.dao.RegisterDao;
import sample.model.Register;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegisterService {

    RegisterDao dao;

    GroupDao groupDao;


    public RegisterService() {
        dao = new RegisterDao();
        groupDao = new GroupDao();

    }
    public ObservableList<Register> getMembersByGroupIdAndDate(String date, int groupId) {
       return  dao.getMembersByGroupIdAndDate(date,groupId);

    }


    public int create(int memberId, int groupId, String lessons_day, String date, boolean onLesson) {
        try {
            return dao.create(memberId, groupId, lessons_day, date, onLesson);
        } catch (SQLException throwables) {
           if(throwables.getMessage().contains("Duplicate entry")){
               ScreenController.alert(Alert.AlertType.ERROR,"error","This member is already in register on this date");
           }
            throwables.printStackTrace();
        }
        return -1;
    }


    public ObservableList<Register> getMembersByGroupId(int groupId) {
        try {
            return dao.getMembersByGroupId(groupId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
