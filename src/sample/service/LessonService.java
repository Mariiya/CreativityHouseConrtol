package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import sample.dao.LessonDao;
import sample.model.TimeTable;
import sample.model.Lessons;

import java.sql.SQLException;
import java.util.List;

public class LessonService {
    private LessonDao lessonDao;
    private ObservableList<Lessons> lessons;

    public LessonService() {
        try {
            lessonDao = new LessonDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            lessons = FXCollections.observableArrayList(lessonDao.getTimeTable());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public ObservableList<TimeTable> getGroupTimeTable() {
        return FXCollections.observableArrayList(lessonDao.getGroupTimeTable());
    }


    public ObservableList<Lessons> getTimeTable() {
        return lessons;
    }

    public void updateWeek(int groupId, String newWeek, String lastWeek) {
        try {
            lessonDao.updateWeek(groupId, newWeek, lastWeek);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTime(int groupId, String newTime, String lastWeek) {
        try {
            lessonDao.updateTime(groupId, newTime, lastWeek);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateRoom(int groupId, int newRoom, String week) {
        try {
            lessonDao.updateRoom(groupId, newRoom, week);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateDuration(int groupId, int newD, String week) {
        try {
            lessonDao.updateDuration(groupId, newD, week);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int groupId, String week) {
        try {
            lessonDao.delete(groupId, week);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void create(int lessonDay, int groupId, String time, int duration) {
        try {
            lessonDao.create(lessonDay, groupId, time, duration);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Integer> getRoomsList() {
        return lessonDao.getRoomsList();
    }
}
