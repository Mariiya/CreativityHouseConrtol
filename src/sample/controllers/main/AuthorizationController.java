package sample.controllers.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controllers.main.ControlledScreen;
import sample.controllers.main.ScreenController;
import sample.controllers.main.ScreensFramework;
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
    public static User activeUser = new User();
    public static int activeUserType = 5;
    private boolean flag=false;

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
        if (activeUser == null) return 5;
        return switch (activeUser.getType()) {
            case "admin" -> 1;
            case "staff" -> 2;
            case "members" -> 3;
            default -> 5;
        };

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        continue_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                activeUser = service.isUser(login_input_field.getText(), password_input_field.getText());
                switch (getActiveUserType()) {
                    case 1:
                        screenController.setScreen(ScreensFramework.screenAHomeID);
                        activeUserType = 1;
                        flag=true;
                        break;
                    case 2:
                        screenController.setScreen(ScreensFramework.screenTHome);
                        activeUserType = 2;
                        flag=true;
                        break;
                    case 3:
                        screenController.setScreen(ScreensFramework.screenMHome);
                        activeUserType = 3;
                        flag=true;
                        break;
                    default:
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("No user found!");
                        alert.showAndWait();
                }
                if (flag) {
                    System.out.println(activeUser.getUserId());
                    System.out.println(activeUser.getUserId()+"Пользователь " + activeUser.getType() + " " + activeUser.getLogin() + " вошел в систему");
                    ScreenController.setNewAction(activeUser.getId(), "Пользователь " + activeUser.getType() + " " + activeUser.getLogin() + " вошел в систему");
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