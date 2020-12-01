package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Payment;
import sample.model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestsDao extends BaseDaoUtils{

        public int create ( int memberId, int sectionId) throws SQLException {
            return stmnt.executeUpdate("INSERT INTO Requests VALUES\n" +
                    "        (NULL, NULL, false, " + sectionId + ", " + memberId + ");");

        }

    public int updateVerification ( int requestId, boolean verification) throws SQLException {
        return stmnt.executeUpdate("UPDATE Requests set verification="+verification+" where request_id="+requestId+";");

    }



        public ObservableList<Request> getRequestListByManagerSpecialization ( int managerId){
            try (
                    ResultSet rs = stmnt.executeQuery("select request_id,section_name,\n" +
                            "       CONCAT(m.last_name,' ',m.first_name,' age:', (YEAR(CURRENT_DATE) - YEAR(m.birth_date))) as member_name,\n" +
                            "       request_date,s.section_id,m.member_id,requests.section_id\n" +
                            "from requests\n" +
                            "left join sections s on s.section_id = requests.section_id\n" +
                            "left join `groups` g on s.section_id = g.section_id\n" +
                            "left join staff s2 on s2.employee_id = g.manager_id\n" +
                            "left join members m on m.member_id = requests.member_id\n" +
                            "WHERE manager_id=3 AND verification=false;");
            ) {
                List<Request> requestList = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("request_id");
                    String date = rs.getString("request_date");
                    String section_name = rs.getString("section_name");
                    String member_name = rs.getString("member_name");
                    int member_id = rs.getInt("member_id");
                    int section_id = rs.getInt("section_id");
                    Request r = new Request(id, date, member_id, section_id, section_name, member_name, false);
                    requestList.add(r);
                }
                return FXCollections.observableArrayList(requestList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }

    }
