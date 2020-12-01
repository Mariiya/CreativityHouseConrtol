package sample.dao;

import sample.model.Section;
import sample.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsDao extends BaseDaoUtils {
public List<String> getAllTypes(){
    try (
        ResultSet rs = stmnt.executeQuery("SELECT DISTINCT (case when type LIKE '%групповые%' then 'Group' else 'Individual'end) as type from sections");
    ){
        List<String>types=new ArrayList<>();
        while(rs.next()){
            String type = rs.getString("type");
            types.add(type);
        }
        return types;
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
return null;
}

    public HashMap<String,Integer> getAllSectionsNames(){
        try (
                ResultSet rs = stmnt.executeQuery("SELECT DISTINCT section_id,CONCAT(section_name,' ', type) as section_name from sections");
        ){
            HashMap<String,Integer> sections_names=new HashMap<>();
            while(rs.next()){
                int id  = rs.getInt("section_id");
                String name = rs.getString("section_name");
                sections_names.put(name,id);
            }
            return sections_names;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Section> getSectionList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from sections");
        ) {
            List<Section> sectionsList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("section_id");
                String name = rs.getString("section_name");
                String type = rs.getString("type");
                int week_lessons_num = rs.getInt("week_lessons_num");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                Section user = new Section(id, name, type, week_lessons_num, price, description);
                sectionsList.add(user);
            }
            return sectionsList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateName(int id, String newName) throws SQLException {
        stmnt.executeUpdate("UPDATE sections set section_name='" + addSlashes(newName) + "' WHERE section_id=" + id + ";");
    }

    public void updateDescription(int id, String newDes) throws SQLException {
        stmnt.executeUpdate("UPDATE sections set description='" + addSlashes(newDes) + "' WHERE section_id=" + id + ";");
    }

    public void updateType(int id, String newType) throws SQLException {
        stmnt.executeUpdate("UPDATE sections set type='" + switchType(newType) + "' WHERE section_id=" + id + ";");
    }

    public void updatePrice(int id, float price) throws SQLException {
        stmnt.executeUpdate("UPDATE sections set price=" + price + " WHERE section_id=" + id + ";");
    }
    public void updateLessonsNumber(int id, float lessonsNumber) throws SQLException {
        stmnt.executeUpdate("UPDATE sections set week_lessons_num=" +lessonsNumber + " WHERE section_id=" + id + ";");
    }
    public void delete(Integer id) throws SQLException {
        stmnt.execute("DELETE FROM sections WHERE section_id=" + id + ";");
    }
    public void create(String name,String type, int less_num,float price,String description) throws SQLException {

        stmnt.execute("INSERT INTO Sections VALUES (NULL,'"+addSlashes(name)+"','"+switchType(type)+"',"+less_num+","+price+",'"+addSlashes(description)+"');");
    }
    String switchType(String type){
        String typeUpdated;
        switch (type){
            case "Individual":typeUpdated="индивидуальные занятия";
                break;
            case "Group": typeUpdated="групповые занятия";
                break;
            default:typeUpdated="индивидуальные занятия";
        }
        return typeUpdated;
    }
}


