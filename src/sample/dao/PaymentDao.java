package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Payment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    private Connection connection;
    private Statement stmnt;

    public PaymentDao() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public int create(String pDate, double amount, String prefCet, int memberId, int groupID) throws SQLException {
        return stmnt.executeUpdate("INSERT INTO Payments VALUES\n" +
                "        (NULL, '" + pDate + "', " + amount + ", '" + prefCet + "', " + memberId + ", " + groupID + ");");

    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ObservableList<Payment> getPaymentsList() {
        try (
                ResultSet rs = stmnt.executeQuery("select payment_id,payment_date,preferential_category,amount,payments.member_id,payments.group_id,\n" +
                        "       CONCAT(section_name,' ',`groups`.age_min,'-',groups.age_max) as `section_name` ,\n" +
                        "       CONCAT(m.first_name,' ', m.last_name) as member_name from payments\n" +
                        "LEFT JOIN `groups`\n" +
                        "on `groups`.group_id=payments.group_id\n" +
                        "left join sections s on `groups`.section_id = s.section_id\n" +
                        "left join members m on payments.member_id = m.member_id\n" +
                        "where payment_date > LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 YEAR));");
        ) {
            return fill(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    ObservableList<Payment> fill(ResultSet rs) throws SQLException {
        List<Payment> paymentList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("payment_id");
            String paymentDate = rs.getString("payment_date");
            double amount = rs.getDouble("amount");
            String prefCat = rs.getString("preferential_category");
            String section_name = rs.getString("section_name");
            String member_name = rs.getString("member_name");
            int member_id = rs.getInt("member_id");
            int group_id = rs.getInt("group_id");
            Payment payment = new Payment(id, paymentDate, amount, prefCat, member_id, group_id, member_name, section_name);
            paymentList.add(payment);
        }
        return FXCollections.observableArrayList(paymentList);
    }

    public ObservableList<Payment> getPaymentListFromTo(String from, String to) {

        try (
                ResultSet rs = stmnt.executeQuery("select payment_id,payment_date,preferential_category,amount,payments.member_id,payments.group_id,CONCAT(section_name,' ',`groups`.age_min,'-',groups.age_max) as `section_name`,\n" +
                        "       CONCAT(m.first_name,' ', m.last_name) as member_name from payments\n" +
                        "                                                                     LEFT JOIN `groups`\n" +
                        "                                                                               on `groups`.group_id=payments.group_id\n" +
                        "                                                                     left join sections s on `groups`.section_id = s.section_id\n" +
                        "                                                                     left join members m on payments.member_id = m.member_id\n" +
                        "where payment_date BETWEEN '" + from + "' AND '" + to + "';");
        ) {

            return fill(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Payment> getGroupsForMember(String fname, String lname, String phone) throws SQLException {
        ResultSet rs = stmnt.executeQuery("select payments.group_id,payments.member_id,payments.preferential_category, CONCAT(s.section_name,' ',age_min,'-',age_max,' ',s.type) as `section_name`" +
                        " from payments left join members m on m.member_id = payments.member_id  " +
                        "left join `groups` g on g.group_id = payments.group_id " +
                "left join sections s on g.section_id = s.section_id" +
                " WHERE m.first_name LIKE '%"+fname+"%' AND m.last_name LIKE '%"+lname+"%' AND m.parent_phone_number LIKE '%"+phone+"%';");
        {
            List<Payment> groupMember = new ArrayList<>();
            while(rs.next()) {
                int idG = rs.getInt("group_id");
                int idM = rs.getInt("Member_id");
                String group_name=rs.getString("section_name");
                String cat=rs.getString("preferential_category");
                Payment payment=new Payment(1," ",1,cat,idM,idG,group_name," ");
                groupMember.add(payment);
            }
            return groupMember;
        }
    }
}
