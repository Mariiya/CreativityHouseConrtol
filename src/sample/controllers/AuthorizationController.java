package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.dao.UserDao;
import sample.model.User;
import sample.service.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable, ControlledScreen {

    @FXML
    private PasswordField password_input_field;

    @FXML
    private TextField login_input_field;

    @FXML
    private Button continue_btn;

    private UserDao userDao;
    private UserService service;
    ScreenController screenController;
    public static User activeUser;
    public static int activeUserType=5;
    public AuthorizationController() throws SQLException {
        userDao = new UserDao();
        service = new UserService();
    }

    public void setScreenParent(ScreenController screen) {
        screenController = screen;
    }


    public static User getActiveUser() {
        return activeUser;
    }

    public static int getActiveUserType() {
        return switch (activeUser.getType()) {
            case "admin" -> 0;
            case "staff" -> 1;
            case "members" -> 2;
            default -> 5;
        };

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        continue_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                activeUser=service.isUser(login_input_field.getText(),password_input_field.getText());
                switch (getActiveUserType()) {
                    case 0:
                        screenController.setScreen(ScreensFramework.screenAHomeID);
                        activeUserType=0;
                        break;
                    case 1:
                        screenController.setScreen(ScreensFramework.screenTHome);
                        activeUserType=1;
                        break;
                    case 2:
                        screenController.setScreen(ScreensFramework.screenMHome);
                        activeUserType=2;
                        break;
                    default:
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("No user found!");
                        alert.showAndWait();
                }
            }
        });
    }

    @FXML
    public void stop() throws Exception {
        if (userDao != null) {
            userDao.shutdown();
        }
    }


}