package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dao.UserDao;
import sample.model.User;
import sample.service.StageService;
import sample.service.UserService;

import java.io.IOException;
import java.sql.SQLException;


public class AdminHomePage {

    @FXML
    private Button manage_btn;

    @FXML
    private Button delete_users_btn;

    @FXML
    private TableView<User> users_table;

    @FXML
    private Button log_out_btn;

    @FXML
    private Button edit_users_btn;

    @FXML
    private Button users_history_btn;



    private UserService service;
    private StageService stageService;

    public AdminHomePage() throws SQLException {
        service = new UserService();
        stageService=new StageService();
        users_table=new TableView<User>();
      //  service.fillTable( users_table);
    }


    @FXML
    void initialize() throws SQLException {
        users_table.setEditable(true);

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(107);
       idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn typeCol = new TableColumn("Type");
       typeCol.setMinWidth(107);
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));

        TableColumn loginCol = new TableColumn("Login");
        loginCol.setMinWidth(107);
        loginCol.setCellValueFactory(
                new PropertyValueFactory<>("login"));


        TableColumn passwordCol = new TableColumn("Password");
        passwordCol.setMinWidth(107);
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password"));


        TableColumn userIdCol = new TableColumn("userId");
        userIdCol.setMinWidth(107);
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<>("userId"));

        users_table.setItems(service.getAllUsers());
        users_table.getColumns().addAll(idCol, typeCol,loginCol,passwordCol,userIdCol);


        log_out_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = (Stage)  log_out_btn.getScene().getWindow();
                stageService.loadStage("/sample/view/autorisationView.fxml");
                stage.close();
            }
        });

        manage_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        users_history_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        edit_users_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        delete_users_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });


    }


}
