package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import sample.model.User;
import sample.service.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn.CellEditEvent;


public class AdminHomePage implements Initializable,ControlledScreen {

    @FXML
    private Button manage_btn, delete_users_btn;
    @FXML
    private TableView<User> users_table;
    @FXML
    private Button log_out_btn, edit_users_btn, users_history_btn, done_edit_btn;

    private UserService service;
    private Stage stage;
    private ScreenController screenController;

    public AdminHomePage() throws SQLException {
        service = new UserService();
        users_table = new TableView<User>();
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController=screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(107);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setMinWidth(107);
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        typeCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        typeCol.setOnEditCommit(
                new EventHandler<CellEditEvent<User, String>>() {
                    @Override
                    public void handle(CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setType(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        service.updateType(id, t.getNewValue());
                    }
                }
        );

        TableColumn loginCol = new TableColumn("Login");
        loginCol.setMinWidth(107);
        loginCol.setCellValueFactory(
                new PropertyValueFactory<>("login"));
        loginCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        loginCol.setOnEditCommit(
                new EventHandler<CellEditEvent<User, String>>() {
                    @Override
                    public void handle(CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLogin(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        service.updateLogin(id, t.getNewValue());
                    }
                }
        );


        TableColumn passwordCol = new TableColumn("Password");
        passwordCol.setMinWidth(107);
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password"));
        passwordCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        passwordCol.setOnEditCommit(
                new EventHandler<CellEditEvent<User, String>>() {
                    @Override
                    public void handle(CellEditEvent<User, String> t) {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPassword(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        service.updatePassword(id, t.getNewValue());
                    }
                }
        );


        TableColumn userIdCol = new TableColumn("userId");
        userIdCol.setMinWidth(107);
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<>("userId"));

        users_table.setItems(service.getAllUsers());
        users_table.getColumns().addAll(idCol, typeCol, loginCol, passwordCol, userIdCol);


        log_out_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                screenController.setScreen(ScreensFramework.screenMainID);
            }
        });

        manage_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                screenController.setScreen(ScreensFramework.screenAManage);
            }
        });

        users_history_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });

        edit_users_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                users_table.setEditable(true);
                done_edit_btn.setDisable(false);
            }
        });

        delete_users_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int index = users_table.getSelectionModel().getSelectedIndex();
                User user = users_table.getItems().get(index);
                service.deleteUser(user.getId());
                users_table.getItems().remove(index);

            }
        });

        done_edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                users_table.setEditable(false);
                done_edit_btn.setDisable(true);
            }
        });
    }



}
