package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import sample.dao.LessonDao;
import sample.model.Lesson;
import sample.model.TimeTable;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonService {
    private LessonDao lessonDao;
    private ObservableList<Lesson> allLessons;
    private ObservableList<TimeTable> timeTable;

    public LessonService() throws SQLException {
        lessonDao = new LessonDao();
        allLessons = FXCollections.observableArrayList(lessonDao.getLessonsList());
        timeTable = FXCollections.observableArrayList(lessonDao.getTimeTable());
    }


    public ObservableList<Lesson> getAllLessons() {
        return allLessons;
    }

    public ObservableList<TimeTable> getTimeTable() {
        return timeTable;
    }

    public void updateWeek(int groupId, String newWeek, String lastWeek)  {
        try {
            lessonDao.updateWeek(groupId, newWeek, lastWeek);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTime(int groupId, String newTime, String lastWeek)  {
        try {
            lessonDao.updateTime(groupId, newTime, lastWeek);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateRoom(int groupId, int newRoom, String week)  {
        try {
            lessonDao.updateRoom(groupId, newRoom, week);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateDuration(int groupId, int newD, String week)  {
        try {
            lessonDao.updateDuration(groupId, newD, week);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int groupId, String week)  {
        try {
            lessonDao.delete(groupId, week);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<Integer> getRoomsList() {
       return lessonDao.getRoomsList();
    }
}
