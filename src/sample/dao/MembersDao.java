package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Member;
import sample.model.Request;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MembersDao extends  BaseDaoUtils{

    public int create(String fname,String lname,String pname, String pphone,String birthDate,String birtCer, String medDate) throws SQLException {
        return  stmnt.executeUpdate("INSERT INTO Members VALUES\n" +
                "        (NULL, '"+addSlashes(fname)+"', '"+addSlashes(lname)+"', '"+addSlashes(birthDate)+"', '"+ addSlashes(birtCer)+"', '"+medDate+"', '"+addSlashes(pname)+"', '"+addSlashes(pphone)+"')\n" +
                "        ;");

    }
    public int getLastAddedMember() throws SQLException {
        ResultSet rs = stmnt.executeQuery("SELECT max(member_id) as max from `members`;");
        int lastId=0;
        while (rs.next()) {
            lastId= rs.getInt("max");
        }
        return lastId;
    }

    public List<Member> getMembersList() {
        try (
                ResultSet rs = stmnt.executeQuery("select * from members");
        ) {
            List<Member> memberList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("member_id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String birth_certif = rs.getString("birth_certificate");
                String bdate = rs.getString("birth_date");
                String mdate = rs.getString("medical_certificate_date");
                String pname = rs.getString("parent_name");
                String pphone = rs.getString("parent_phone_number");

                Member member = new Member(id, fname, lname, bdate, birth_certif, mdate, pname, pphone);
                memberList.add(member);
            }
            return memberList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void updateMedCertificate(int memberId, String newDate) throws SQLException {
        stmnt.executeUpdate("UPDATE members set medical_certificate_date='" + newDate + "' WHERE member_id=" + memberId + ";");
    }

    public void updateLastName(int memberId, String newLastName) throws SQLException {
        stmnt.executeUpdate("UPDATE members set last_name='" + addSlashes(newLastName) + "' WHERE member_id=" + memberId + ";");
    }

    public void updatePhoneNumber(int memberId, String newPhone) throws SQLException {
        stmnt.executeUpdate("UPDATE members set parent_phone_number='" + addSlashes(newPhone) + "' WHERE member_id=" + memberId + ";");
    }

    public void updateParentName(int memberId, String newParentName) throws SQLException {
        stmnt.executeUpdate("UPDATE members set parent_name='" + addSlashes(newParentName) + "' WHERE member_id=" + memberId + ";");
    }


    public void delete(int memberId) throws SQLException {
        stmnt.execute("DELETE FROM members WHERE member_id=" + memberId + ";");
    }
}
