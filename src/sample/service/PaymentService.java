package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.controllers.ScreenController;
import sample.dao.GroupDao;
import sample.dao.PaymentDao;

import sample.dao.SectionsDao;
import sample.model.Payment;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PaymentService {

    PaymentDao paymentDao;
    GroupDao groupDao;
    private ObservableList<Payment> allPayments;

    public PaymentService() {
        try {
            paymentDao = new PaymentDao();
            groupDao=new GroupDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        allPayments = FXCollections.observableArrayList(paymentDao.getPaymentsList());

    }

    public List<Payment> getGroupsForMember(String fname, String lname, String phone)  {
        try {
            return paymentDao.getGroupsForMember(fname, lname, phone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int create(String pDate, double amount, String prefCet, int memberId, int groupID) {
        try {
            return paymentDao.create(pDate, amount, prefCet, memberId, groupID);
        } catch (SQLException throwables) {
            if(throwables.getMessage().equals("Amount is not enough")){
                ScreenController.alert(Alert.AlertType.ERROR,"Wrong Amount","Amount is not enough");
            }
            throwables.printStackTrace();
        }
        return - 1;
    }

    public ObservableList<Payment> getPaymentList() {
      return  allPayments;
    }

   public  ObservableList<Payment> getPaymentListByDate(LocalDate from, LocalDate to){
        return paymentDao.getPaymentListFromTo(from.toString(),to.toString());
   }

}
