package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DefaultStringConverter;
import sample.controllers.main.AuthorizationController;
import sample.controllers.main.ScreenController;
import sample.controllers.main.ScreensFramework;
import sample.model.Group;
import sample.model.Request;
import sample.service.GroupService;
import sample.service.PaymentService;
import sample.service.RequestService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static sample.controllers.main.AuthorizationController.activeUser;


public class RequestsController {
    @FXML
    private TableColumn<Request, String> approve_col, section_col, member_col, date_col;
    @FXML
    private Button edit_btn, done_btn, create_group_btn;
    @FXML
    private TableView<Request> request_table;

    private ScreenController screenController;
    private RequestService service;
    private GroupService groupService;
    private PaymentService paymentService;
    ObservableList<Request> requests;
    ObservableList<Group> groups;
    List<String> groupsNames = new ArrayList<>();


    public RequestsController() throws SQLException {
        service = new RequestService();
        groupService = new GroupService();
        paymentService = new PaymentService();
        request_table = new TableView<>();
        request_table.setEditable(false);
        requests = service.getRequestListByManagerSpecialization(AuthorizationController.activeUser.getUserId());
        groups = groupService.getGroupsListByManagerId(activeUser.getUserId());
    }

    void fillTable() {
        member_col.setCellValueFactory(
                new PropertyValueFactory<>("memberName"));
        date_col.setCellValueFactory(
                new PropertyValueFactory<>("requestDate"));
        section_col.setCellValueFactory(
                new PropertyValueFactory<>("sectionName"));
        approve_col.setCellValueFactory(
                new PropertyValueFactory<>("group"));

        for (int i = 0; i < groups.size(); i++) {
            groupsNames.add(groups.get(i).getSectionName());
        }
        approve_col.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), FXCollections.observableArrayList(groupsNames)));
        request_table.setItems(requests);
    }

    @FXML
    public void initialize() {
        fillTable();
        done_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                request_table.setEditable(false);
                done_btn.setDisable(true);

            }
        });
        edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                request_table.setEditable(true);
                done_btn.setDisable(false);
            }
        });
        approve_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Request, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Request, String> t) {
                int index = -1;
                ((Request) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setGroup(t.getNewValue());
                int requestId = t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).getRequestId();
                int memberId = t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).getMemberId();
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getSectionName().contains(t.getNewValue())) {
                        index = i;
                        break;
                    }
                }
                if (index < 0) {
                    ScreenController.alert(Alert.AlertType.ERROR, "reques is not approved", "error during approving");
                } else {
                    if (groupService.updateMaxMemberNum(groups.get(index).getGroupId(), groups.get(index).getMaxMemberNum() - 1) != -1) {

                        if (paymentService.create("2020-01-01", -1, "", memberId, groups.get(index).getGroupId()) != -1) {
                            service.updateVerification(requestId, true);
                        } else {
                            ScreenController.alert(Alert.AlertType.ERROR, "reques is not approved", "error during inserting payment");
                        }
                    } else {
                        ScreenController.alert(Alert.AlertType.ERROR, "reques is not approved", "error during updating groups member number");
                    }
                }
            }
        });
        create_group_btn.setOnAction(e ->screenController.setScreen(ScreensFramework.screenGroups));
    }

}
