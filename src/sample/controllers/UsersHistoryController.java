package sample.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.model.UsersHistory;
import sample.service.UserHistoryService;

public class UsersHistoryController implements ControlledScreen {
    @FXML
    public TableView<UsersHistory> users_history_table;
    @FXML
    private TableColumn<UsersHistory, String> type_col, date_col, user_col, time_col, action_col;

    @FXML
    private Button clean_btn;

    public static UserHistoryService service;
    private ScreenController screenController;

    public UsersHistoryController() {
        service = new UserHistoryService();
        users_history_table = new TableView<>();
        screenController = new ScreenController();
    }


    void fillTable() {
        user_col.setCellValueFactory(
                new PropertyValueFactory<>("userName"));
        type_col.setCellValueFactory(
                new PropertyValueFactory<>("userType"));
        date_col.setCellValueFactory(
                new PropertyValueFactory<>("actionDate"));
        time_col.setCellValueFactory(
                new PropertyValueFactory<>("actionTime"));
        action_col.setCellValueFactory(
                new PropertyValueFactory<>("descr"));
        users_history_table.setItems(service.getAllHistory());
    }

    @FXML
    public void initialize() {
        fillTable();

        clean_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                service.clear();
                users_history_table.getItems().clear();
            }
        });
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }
}
