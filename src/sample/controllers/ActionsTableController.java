package sample.controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.controllers.main.ScreenController;
import sample.model.Action;
import sample.service.ActionService;

import static sample.controllers.main.AuthorizationController.activeUser;


public class ActionsTableController {

    @FXML
    private TableColumn<Action, String> action_title_col, group_col, manager_col, event_col;
    @FXML
    private Button edit_btn, done_btn, delete_btn;
    @FXML
    private TableColumn<Action, Integer> num_col;
    @FXML
    private TableView<Action> actions_table;
    private ScreenController screenController;
    private int eventId;
    ActionService service;

    public ActionsTableController(int eventId)  {
        screenController = new ScreenController();
        service = new ActionService();
        actions_table = new TableView<Action>();
        this.eventId = eventId;
    }


    void fillTable() {
       action_title_col.setCellValueFactory(
                new PropertyValueFactory<>("eventId"));
      num_col.setCellValueFactory(
                new PropertyValueFactory<>("number"));
       group_col.setCellValueFactory(
                new PropertyValueFactory<>("groupName"));
        event_col.setCellValueFactory(
                new PropertyValueFactory<>("eventName"));
       manager_col.setCellValueFactory(
                new PropertyValueFactory<>("managerName"));
        action_title_col.setCellValueFactory(
                new PropertyValueFactory<>("actionName"));
        action_title_col.setCellFactory(TextFieldTableCell.<Action>forTableColumn());
        actions_table.setItems(service.getEventList(eventId));
    }


    @FXML
    public void initialize() {
    fillTable();

    action_title_col.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Action, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Action, String> t) {
                    ((Action) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setActionName(t.getNewValue());
                    int eventid = t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getEventId();
                    int num = t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getNumber();
                    service.updateTitle(eventid,num,t.getNewValue());
                }
            }
    );
        delete_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = actions_table.getSelectionModel().getSelectedIndex();
                Action a = actions_table.getItems().get(index);
                service.delete(a.getEventId(),a.getNumber());
                actions_table.getItems().remove(index);
                ScreenController.setNewAction(activeUser.getId(),"Выступление "+a.getactioneName()+" удалено");
            }
        });
        done_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actions_table.setEditable(false);
                done_btn.setDisable(true);

            }
        });
        edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actions_table.setEditable(true);
                done_btn.setDisable(false);
            }
        });
    }
}
