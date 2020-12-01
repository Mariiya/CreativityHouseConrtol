package sample.dao;

import sample.model.Member;
import sample.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao extends BaseDaoUtils{

    public int create(String fname,String lname,String pos, String phone,int max_hours,String spec) throws SQLException {
        return  stmnt.executeUpdate("INSERT INTO Staff VALUES\n" +
                "        (NULL, '"+addSlashes(fname)+"', '"+addSlashes(lname)+"', '"+addSlashes(phone)+"', '"+addSlashes(pos)+"', "+max_hours+", '"+addSlashes(spec)+"');");
    }
    public int getLastAddedEmployee() throws SQLException {
        ResultSet rs = stmnt.executeQuery("SELECT max(employee_id) as max from `staff`;");
        int lastId=0;
        while (rs.next()) {
            lastId= rs.getInt("max");
        }
        return lastId;
    }


    public List<Employee> getStaffList() {
        try (
                ResultSet rs = stmnt.executeQuery("select * from staff");
        ) {
            List<Employee> empList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String phone= rs.getString("phone_number");
                String pos = rs.getString("position");
                int mhours = rs.getInt("max_work_hours");
                String spec= rs.getString("specialization");

                Employee emp = new Employee(id, fname, lname,phone,spec,mhours,pos);
               empList.add(emp);
            }
            return empList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updatePosition(int empId, String newPosition) throws SQLException {
        stmnt.executeUpdate("UPDATE staff set position ='" + addSlashes(newPosition) + "' WHERE employee_id=" + empId + ";");
    }

    public void updateLastName(int empId, String newLastName) throws SQLException {
        stmnt.executeUpdate("UPDATE staff set last_name='" + addSlashes(newLastName) + "' WHERE employee_id=" + empId + ";");
    }

    public void updateMaxWHours(int empId, int newHours) throws SQLException {
        stmnt.executeUpdate("UPDATE staff set max_work_hours =" + newHours + " WHERE employee_id=" + empId + ";");
    }

    public void updateSpecialization(int empId, String newSpecialization) throws SQLException {
        stmnt.executeUpdate("UPDATE staff set specialization ='" + addSlashes(newSpecialization) + "' WHERE employee_id=" + empId + ";");
    }
    public void updatePhone(int empId, String newphone) throws SQLException {
        stmnt.executeUpdate("UPDATE staff set phone_number ='" + newphone + "' WHERE employee_id=" + empId + ";");
    }
    public void delete(int empId) throws SQLException {
        stmnt.execute("DELETE FROM staff WHERE employee_id=" + empId + ";");
    }
}
