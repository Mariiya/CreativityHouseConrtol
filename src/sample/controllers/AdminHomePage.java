package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.dao.UserDao;
import sample.service.UserService;

import java.io.IOException;
import java.sql.SQLException;



public class AdminHomePage{

    @FXML
    private Button manage_btn;

    @FXML
    private Button delete_users_btn;

    @FXML
    private TableView<?> users_table;

    @FXML
    private Button log_out_btn;

    @FXML
    private Button edit_users_btn;

    @FXML
    private Button users_history_btn;


    private UserDao userDao;
    private UserService service;


    public AdminHomePage() throws SQLException {
        userDao = new UserDao();
        service = new UserService();
    }


    @FXML
    void initialize() throws SQLException {

        log_out_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            loadStage("/sample/view/autorisationView.fxml");
            }
        });

    }


    public void loadStage(String viewPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource(viewPath)));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stageNew = new Stage();
        stageNew.setScene(new Scene(root));
        stageNew.showAndWait();
    }

}
