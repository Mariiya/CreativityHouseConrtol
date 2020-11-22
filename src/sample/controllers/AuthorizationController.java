package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dao.UserDao;

import sample.service.StageService;
import sample.service.UserService;

import java.sql.SQLException;

public class AuthorizationController {

    @FXML
    private PasswordField password_input_field;

    @FXML
    private TextField login_input_field;

    @FXML
    private Button continue_btn;

    private UserDao userDao;
    private UserService service;
private StageService stageService;

    public AuthorizationController() throws SQLException {
        userDao = new UserDao();
        service = new UserService();
        stageService=new StageService();
    }


    @FXML
    void initialize() throws SQLException {

        continue_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) continue_btn.getScene().getWindow();
                switch (service.isUser(password_input_field.getText(), login_input_field.getText())) {
                    case 0:
                        stage.close();
                        stageService.loadStage("/sample/view/admin_home_page.fxml");
                        break;
                    case 1:
                        stage.close();
                        stageService.loadStage("/sample/view/teacher_home_page.fxml");
                        break;
                    case 2:
                        stage.close();
                        stageService.loadStage("/sample/view/member_home_page.fxml");
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