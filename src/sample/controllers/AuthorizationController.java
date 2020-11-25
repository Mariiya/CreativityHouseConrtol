package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dao.UserDao;

import sample.service.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable,ControlledScreen {

    @FXML
    private PasswordField password_input_field;

    @FXML
    private TextField login_input_field;

    @FXML
    private Button continue_btn;

    private UserDao userDao;
    private UserService service;
   ScreenController screenController;
 public static int activeUser;

    public AuthorizationController() throws SQLException {
        userDao = new UserDao();
        service = new UserService();
    }

    public  void setScreenParent(ScreenController screen){
        screenController=screen;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        continue_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (service.isUser(password_input_field.getText(), login_input_field.getText())) {
                    case 0:
                        screenController.setScreen(ScreensFramework.screenAHomeID);
                        activeUser=0;
                        break;
                    case 1:
                        screenController.setScreen(ScreensFramework.screenTHome);
                        activeUser=1;
                        break;
                    case 2:
                        screenController.setScreen(ScreensFramework.screenMHome);
                        activeUser=2;
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