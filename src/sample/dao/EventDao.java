package sample.dao;

import sample.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private Connection connection;
    private Statement stmnt;

    public EventDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Event> getEventList() {
        try (
                ResultSet rs = stmnt.executeQuery("select * from events");
        ) {
            List<Event> eventList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("event_id");
                String time = rs.getString("event_time");
                String date = rs.getString("event_date");
                String name = rs.getString("event_name");
                String address = rs.getString("event_addr");
                Event event= new Event(id,name,address,date,time);
                eventList.add(event);
            }
            return eventList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean create (String time,String name,String date,String addr) throws SQLException {
       return stmnt.execute("INSERT INTO Events VALUES(NULL,'" +name+ "','"+addr+"','"+date+"','"+time+"');");
    }
    public int delete(int event_id) throws SQLException {
        return stmnt.executeUpdate("DELETE FROM  Events WHERE event_id="+event_id+";");
    }
    public int updateName(int event_id,String newVal) throws SQLException {
        return stmnt.executeUpdate("UPTADE Events SET event_name='"+newVal+"' WHERE event_id="+event_id+";");
    }
    public int updateAddr(int event_id,String newVal) throws SQLException {
        return stmnt.executeUpdate("UPTADE Events SET event_addr='"+newVal+"' WHERE event_id="+event_id+";");
    }
    public int updateTime(int event_id,String newVal) throws SQLException {
        return stmnt.executeUpdate("UPTADE Events SET event_time='"+newVal+"' WHERE event_id="+event_id+";");
    }
    public int updateDate(int event_id,String newVal) throws SQLException {
        return stmnt.executeUpdate("UPTADE Events SET event_date='"+newVal+"' WHERE event_id="+event_id+";");
    }
}
