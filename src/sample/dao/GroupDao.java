package sample.dao;

import sample.model.Group;
import sample.model.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private Connection connection;
    private Statement stmnt;

    public GroupDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Group> getGroupsList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT s.section_name,g.group_id,g.age_min,g.age_max,g.manager_id,g.section_id,s.section_id,g.max_members_num,\n" +
                        "       CONCAT(e.first_name,' ', e.last_name) as employee_name\n" +
                        "FROM Groups g LEFT JOIN\n" +
                        "    Sections s\n" +
                        "        ON g.section_id=s.section_id\n" +
                        "              LEFT JOIN staff e\n" +
                        "                        ON e.employee_id=g.manager_id\n" +
                        "GROUP BY g.group_id;");
        ) {
            List<Group> sectionsList = new ArrayList<>();
            while (rs.next()) {
                int groupId = rs.getInt("group_id");
                int managerId = rs.getInt("manager_id");
                int sectionId = rs.getInt("section_id");
                String section_name = rs.getString("section_name");
                String manager_name = rs.getString("employee_name");
                int max_memb = rs.getInt("max_members_num");
                int age_min = rs.getInt("age_min");
                int age_max = rs.getInt("age_max");
                Group group = new Group(groupId,age_min,age_max,max_memb,managerId,sectionId,manager_name,section_name);
                sectionsList.add(group);
            }
            return sectionsList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateMaxAge(int id, int newAge) throws SQLException {
        stmnt.executeUpdate("UPDATE groups set max_age=" +newAge + " WHERE group_id=" + id + ";");
    }
    public void updateMinAge(int id, int newAge) throws SQLException {
        stmnt.executeUpdate("UPDATE groups set min_age=" +newAge + " WHERE group_id=" + id + ";");
    }

    public void updateMaxMemberNum(int id, int maxMemberNum) throws SQLException {
        stmnt.executeUpdate("UPDATE groups set max_members_num=" +maxMemberNum + " WHERE group_id=" + id + ";");
    }
    public void delete(Integer id) throws SQLException {
        stmnt.execute("DELETE FROM groups WHERE group_id=" + id + ";");
    }
    public void create(int age_min,int age_max,int max_memb,int manager_id,int section_id) throws SQLException {
        stmnt.execute("INSERT INTO groups VALUES (NULL,"+age_min+","+age_max+","+max_memb+","+manager_id+","+section_id+");");
    }
}




