package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Register;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class RegisterDao extends BaseDaoUtils {

    public int create(int memberId, int groupId, String lessons_day,String date,boolean onLesson) throws SQLException {
        return stmnt.executeUpdate("INSERT INTO register VALUES\n" +
                "        (" + memberId + ", " + groupId + ", " + getWeekBack(lessons_day) + ", '"+date+"',"+onLesson+");");

    }

    public ObservableList<Register> getMembersByGroupId(int groupId) throws SQLException {

        ResultSet rs = stmnt.executeQuery("Select m.member_id, CONCAT(first_name,' ',last_name) as member_name from payments\n" +
                "left join members m on m.member_id = payments.member_id\n" +
                "Where group_id=" + groupId + " AND amount=-1;");
        {
            List<Register> register = new ArrayList<>();
            while (rs.next()) {
                String member_name = rs.getString("member_name");
                int member_id = rs.getInt("member_id");
                Register r = new Register("",member_id,groupId,"",member_name,"",0);
                register.add(r);
            }
            return FXCollections.observableArrayList(register);
        }
    }


   /* public ObservableList<Register> getRegisterByDateAndGroup (String date,int groupId){
        try (
                ResultSet rs = stmnt.executeQuery("");
        ) {
            List<Register> register = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("request_id");
                String section_name = rs.getString("group_name");
                String member_name = rs.getString("member_name");
                int member_id = rs.getInt("member_id");
               Register r=new Register();
                register.add(r);
            }
            return FXCollections.observableArrayList(register);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }*/


        }
