package sample.dao;

import sample.model.User;
import sample.model.UsersHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserHistoryDao {


    private Connection connection;
    private Statement stmnt;

    public UserHistoryDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    public List<UsersHistory> getUserHistoryList() {
        try (
                ResultSet rs = stmnt.executeQuery("select u.user_id,user_type,m.last_name,action_date,action_time, descr from userhistory\n" +
                        "left join users u on userhistory.user_id = u.user_id\n" +
                        "left JOIN members m\n" +
                        "on m.member_id=u.user_num WHERE u.user_type LIKE '%member%'\n" +
                        "union\n" +
                        "select u.user_num,user_type,s.last_name ,action_date,action_time,descr from userhistory\n" +
                        "left join users u on userhistory.user_id = u.user_id\n" +
                        "left join staff s\n" +
                        "on s.employee_id=u.user_num WHERE u.user_type LIKE '%staff%'\n" +
                        "union\n" +
                        "select u.user_num,user_type,'admin' ,action_date,action_time,descr from userhistory\n" +
                        "left join users u on userhistory.user_id = u.user_id\n" +
                        " WHERE u.user_type LIKE '%admin%'\n" +
                        "\n");
        ) {
            List<UsersHistory> userList = new ArrayList<>();
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String type = rs.getString("user_type");
                String name = rs.getString("last_name");
                String date = rs.getString("action_date");
                String time = rs.getString("action_time");
                String descr = rs.getString("descr");
                UsersHistory action = new UsersHistory(user_id,date,time,descr,name,type);
                userList.add(action);
            }
            return userList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public int create(int userId, String descr) throws SQLException {
        return stmnt.executeUpdate("INSERT INTO userHistory VALUES (NULL," + userId + ",NULL,NULL,'" + descr + "');");
    }
    public int clear() throws SQLException {
        return stmnt.executeUpdate("DELETE FROM  userHistory;");
    }
}
