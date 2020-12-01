package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.RequestsDao;
import sample.model.Request;
import sample.model.Section;

import java.sql.SQLException;


public class RequestService {
    private RequestsDao dao;

    public RequestService() throws SQLException {
        dao = new RequestsDao();
    }


    public int updateVerification(int requestId, boolean verification) {
        try {
            return dao.updateVerification(requestId, verification);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int create(int memberId, int sectionId) {
        try {
            return dao.create(memberId, sectionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public ObservableList<Request> getRequestListByManagerSpecialization(int managerId) {
        return FXCollections.observableArrayList(dao.getRequestListByManagerSpecialization(managerId));
    }

}
