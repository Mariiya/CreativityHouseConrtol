package sample.service;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.MembersDao;
import sample.model.Member;
import java.sql.SQLException;
import java.time.LocalDate;


public class MemberService {
    MembersDao membersDao;
    private ObservableList<Member> allMembers;

    public MemberService() {
        membersDao = new MembersDao();
        allMembers = FXCollections.observableArrayList(membersDao.getMembersList());
    }

    public void delete(int memberId) {
        try {
            membersDao.delete(memberId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateMedCertificate(int memberId, String newDate) {
        try {
            membersDao.updateMedCertificate(memberId, newDate);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLastName(int memberId, String newLastName) {
        try {
            membersDao.updateLastName(memberId, newLastName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updatePhoneNumber(int memberId, String newPhone) {
        try {
            membersDao.updatePhoneNumber(memberId, newPhone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateParentName(int memberId, String newParentName) {
        try {
            membersDao.updateParentName(memberId, newParentName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int create(String fname, String lname, String pname,
                          String pphone, String birthDate, String birtCer, LocalDate medDate) {
        try {
            return membersDao.create(fname, lname, pname, pphone, birthDate, birtCer, medDate.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Returning dfalse");
        return -1;
    }
    public int getLastAddedMember()  {
        try {
            return membersDao.getLastAddedMember();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public ObservableList<Member> getAllMembers() {
        return allMembers;
    }
}
