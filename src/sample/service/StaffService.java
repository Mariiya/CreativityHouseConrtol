package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import sample.dao.StaffDao;
import sample.model.Employee;

import java.sql.SQLException;


public class StaffService {

    StaffDao staffDao;
    private ObservableList<Employee> allEmployees;

    public StaffService() {
        staffDao = new StaffDao();
        allEmployees = FXCollections.observableArrayList(staffDao.getStaffList());
    }
    public int create(String fname,String lname,String pos, String phone,int max_hours,String spec)  {
        try {
            return  staffDao.create(fname, lname, pos, phone, max_hours, spec);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void updatePhone(int empId, String newphone){
        try {
            staffDao.updatePhone(empId, newphone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getLastAddedEmployee()  {
        try {
            return staffDao.getLastAddedEmployee();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }



    public ObservableList<Employee> getAllEmployees() {
        return allEmployees;
    }

    public void updatePosition(int empId, String newPosition)  {
        try {
            staffDao.updatePosition(empId, newPosition);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLastName(int empId, String newLastName)  {
        try {
            staffDao.updateLastName(empId, newLastName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateMaxWHours(int empId, int newHours)  {
        try {
            staffDao.updateMaxWHours(empId, newHours);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateSpecialization(int empId, String newSpecialization) {
        try {
            staffDao.updateSpecialization(empId,newSpecialization);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int empId)  {
        try {
            staffDao.delete(empId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
