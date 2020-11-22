package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import sample.model.User;
import sample.service.StageService;
import sample.service.UserService;
import java.sql.SQLException;
import javafx.scene.control.TableColumn.CellEditEvent;



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
    @FXML
    private Button done_edit_btn;

    private UserService service;
    private StageService stageService;
    private Stage stage;
    private Object EventHandler;

    public AdminHomePage() throws SQLException {
        service = new UserService();
        stageService = new StageService();
        users_table = new TableView<User>();
    }


    
    @FXML
    void initialize() throws SQLException {


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
                        int id=t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                          service.updateType(id,t.getNewValue());
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
                        int id=t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        service.updateLogin(id,t.getNewValue());
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
                        int id=t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        service.updatePassword(id,t.getNewValue());
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
                stage = (Stage) log_out_btn.getScene().getWindow();
                stageService.loadStage("/sample/view/autorisationView.fxml");
                stage.close();
            }
        });

        manage_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage = (Stage) manage_btn.getScene().getWindow();
                stage.close();
                stageService.loadStage("/sample/view/admin_manage_page.fxml");
            }
        });

        users_history_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // stage.close();
                // stageService.loadStage("/sample/view/admin_manage_view.fxml");
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
                int index= users_table.getSelectionModel().getSelectedIndex();
            User user=users_table.getItems().get(index);
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
