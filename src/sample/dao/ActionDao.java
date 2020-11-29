package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Action;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionDao {
    private Connection connection;
    private Statement stmnt;

    public ActionDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public  ObservableList<Action>  getActionListByEvemtId(int event_id) {
        try (
                ResultSet rs = stmnt.executeQuery("SELECT a.number, a.action_title,s.section_name," +
                        "events.event_name,g.group_id,CONCAT(e.first_name,' ',e.last_name) as manager\n" +
                        "FROM Actions a LEFT JOIN\n" +
                        "     (GROUPS g LEFT JOIN\n" +
                        "         Sections s\n" +
                        "         ON g.section_id=s.section_id\n" +
                        "         LEFT JOIN Staff  e\n" +
                        "         ON e.employee_id=g.manager_id)\n" +
                        "     ON g.group_id=a.group_id\n" +
                        "              LEFT JOIN Events\n" +
                        "                          ON a.event_id=Events.event_id\n" +
                        "                              WHERE events.event_id="+event_id+";");
        ) {
            List<Action> actionList = new ArrayList<>();
            while (rs.next()) {
                int num = rs.getInt("number");
                int idGroup = rs.getInt("group_id");
                String title = rs.getString("action_title");
                String sectionName = rs.getString("section_name");
                String eventName = rs.getString("event_name");
                String managerName = rs.getString("manager");
                Action act= new Action(event_id,idGroup,sectionName,managerName,title,eventName,num);
               actionList.add(act);
            }
            ObservableList<Action> actionslist= FXCollections.observableList(actionList);
            return  actionslist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean create (String title,int num,int eventId,int groupId) throws SQLException {
        return stmnt.execute("INSERT INTO Actions VALUES('" +title+ "',"+num+","+eventId+","+groupId+");");
    }
    public int delete(int event_id,int number) throws SQLException {
        return stmnt.executeUpdate("DELETE FROM  Actions WHERE event_id="+event_id+" AND number="+number+";");
    }
    public int updateTitle(int event_id,int number,String newVal) throws SQLException {
        return stmnt.executeUpdate("UPTADE Actions SET action_title='"+newVal+"' WHERE event_id="+event_id+" AND number="+number+";");
    }
}
