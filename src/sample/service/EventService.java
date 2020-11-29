package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.EventDao;
import sample.model.Event;
import java.sql.SQLException;
import java.util.List;

public class EventService {

    private EventDao eventDao;
    private ObservableList<Event> allEvents;

    public EventService() throws SQLException {
        eventDao=new EventDao();
        allEvents = FXCollections.observableArrayList(eventDao.getEventList());
    }

    public ObservableList<Event> getEventList() {
        return  allEvents;
    }

    public int create (String time,String name,String date,String addr)  {
        try {
             eventDao.create(time, name, date, addr);
             return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int delete(int event_id)  {
        try {
            return eventDao.delete(event_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int updateName(int event_id,String newVal)  {
        try {
            return eventDao.updateName(event_id,newVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public int updateAddr(int event_id,String newVal)  {
        try {
            return eventDao.updateAddr(event_id,newVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public int updateTime(int event_id,String newVal)  {
        try {
            return eventDao.updateTime(event_id, newVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public int updateDate(int event_id,String newVal)  {
        try {
            return eventDao.updateDate(event_id, newVal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
