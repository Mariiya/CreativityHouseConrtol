package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.LessonDao;
import sample.dao.SectionsDao;
import sample.model.Lesson;
import sample.model.Section;
import sample.model.TimeTable;

import java.sql.SQLException;
import java.util.List;

public class SectionsService {

    private SectionsDao sectionsDao;
    private ObservableList<Section> allSections;

    public SectionsService() throws SQLException {
        sectionsDao = new SectionsDao();
        allSections = FXCollections.observableArrayList(sectionsDao.getSectionList());
    }

    public List<String> getAllTypes() {
        return sectionsDao.getAllTypes();
    }

    public void updateName(int id, String newName) {
        try {
            sectionsDao.updateName(id, newName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateDescription(int id, String newDes) {
        try {
            sectionsDao.updateDescription(id, newDes);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateType(int id, String newType) {
        try {
            sectionsDao.updateType(id, newType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updatePrice(int id, float price) {
        try {
            sectionsDao.updatePrice(id, price);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLessonsNumber(int id, float lessonsNumber) {
        try {
            sectionsDao.updateLessonsNumber(id, lessonsNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try {
            sectionsDao.delete(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void create(String name, String type, int less_num, float price, String description) {
        try {
            sectionsDao.create(name, type, less_num, price, description);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Section> getAllSections() {
        return allSections;
    }


}
