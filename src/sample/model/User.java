package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty login = new SimpleStringProperty(this, "login");
    private final StringProperty password = new SimpleStringProperty(this, "password");
    private final StringProperty type = new SimpleStringProperty(this, "type");
    private final IntegerProperty userId = new SimpleIntegerProperty(this, "userId");

    public IntegerProperty idProperty() {
        return id;
    }

    public final Integer getId() {
        return idProperty().get();
    }

    public final void setId(int id) {
        idProperty().set(id);
    }

    public StringProperty loginProperty() {
        return login;
    }

    public final String getLogin() {
        return loginProperty().get();
    }

    public final void setLogin(String login) {
        loginProperty().set(login);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public final String getPassword() {
        return passwordProperty().get();
    }

    public final void setPassword(String password) {
        passwordProperty().set(password);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public final String getType() {
        return typeProperty().get();
    }

    public final void setType(String type) {
        typeProperty().set(type);
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
     public User(){
        
     }
    public User(int id, String login, String password, String type, int userId) {
        setLogin(login);
        setPassword(password);
        setType(type);
        setId(id);
        setUserId(userId);
    }
}
