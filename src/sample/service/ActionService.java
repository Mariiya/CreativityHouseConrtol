package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.ActionDao;
import sample.model.Action;
import sample.model.Group;

import java.sql.SQLException;
import java.util.ArrayList;

public class ActionService {

    private ActionDao actionDao;
    private ObservableList<Action> allActionsByEvent;

    public ActionService()  {
        try {
            actionDao=new ActionDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Action> getEventList(int eventId) {
        ObservableList<Action> actions=actionDao.getActionListByEvemtId(eventId);
        return   FXCollections.observableArrayList(actions);
    }

    public boolean create (String title,int num,int eventId,int groupId)  {
        try {
            return actionDao.create(title, num, eventId, groupId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public int delete(int event_id,int number)  {
        try {
            return  actionDao.delete(event_id, number);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int updateTitle(int event_id,int number,String newVal) {
        try {
            return  actionDao.updateTitle(event_id, number, newVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

}
