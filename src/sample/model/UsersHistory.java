package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsersHistory {

    private final IntegerProperty userId = new SimpleIntegerProperty(this, "userId");
    private final StringProperty actionDate = new SimpleStringProperty(this, "actionDate");
    private final StringProperty actionTime = new SimpleStringProperty(this, "actionTime");
    private final StringProperty descr = new SimpleStringProperty(this, "descr");
    private final StringProperty userName = new SimpleStringProperty(this, " userName");
    private final StringProperty userType = new SimpleStringProperty(this, "userType");


    public StringProperty actionDateProperty() {
        return actionDate;
    }
    public final String getactionDate() {
        return actionDateProperty().get();
    }
    public final void setactionDate(String actionDate) {
        actionDateProperty().set(actionDate);
    }

    public StringProperty actionTimeProperty() {
        return actionTime;
    }
    public final String getActionTime() {
        return actionTimeProperty().get();
    }
    public final void setActionTime(String actionTime) {
        actionTimeProperty().set(actionTime);
    }

    public StringProperty  userNameProperty() {
        return  userName;
    }
    public final String getUserName() {
        return  userNameProperty().get();
    }
    public final void setUserName(String  userName) {
        userNameProperty().set( userName);
    }

    public StringProperty  userTypeProperty() {
        return  userType;
    }
    public final String getUserType() {
        return  userTypeProperty().get();
    }
    public final void setUserType(String  userType) {
        userTypeProperty().set(userType);
    }

    public StringProperty  descrProperty() {
        return  descr;
    }
    public final String getDescr() {
        return  descrProperty().get();
    }
    public final void setDescr(String  descr) {
        descrProperty().set( descr);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }
    public final Integer getUserId() {
        return userIdProperty().get();
    }
    public final void setUserId(int userId) {
        userIdProperty().set(userId);
    }

    public UsersHistory(int userId,String actionDate, String actionTime, String descr,String userName,String userType) {
        setUserId(userId);
        setactionDate(actionDate);
        setActionTime(actionTime);
        setDescr(descr);
        setUserName(userName);
        setUserType(userType);
    }
}
