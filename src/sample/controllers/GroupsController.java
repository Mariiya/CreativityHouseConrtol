package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.model.Group;
import sample.model.Section;
import sample.service.GroupService;
import sample.service.SectionsService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.controllers.AuthorizationController.activeUser;

public class GroupsController implements Initializable, ControlledScreen {
    @FXML
    private ImageView back_img;
    @FXML
    private ComboBox<?> sections_box_input;
    @FXML
    private TableColumn<Group, String> teacher_col, type_col, section_name_col;
    @FXML
    private Button create_lessons_btn, delete_group_btn, view_members_btn, create_group_btn, done_group_btn, edit_group_btn;
    @FXML
    private CheckBox monday_check_box, tue_check_box, fri_check_box, sat_check_box, wed_check_box, sun_check_box, thu_check_box;
    @FXML
    private TableColumn<Group, Integer> age_min_col, age_max_col, free_places_col;
    @FXML
    private TableView<Group> groups_table;
    @FXML
    private TextField monday_time_input, age_max_input, sun_time_input, thu_time_input, fri_time_input, sat_time_input, wed_time_input, tue_time_input, age_min_input, max_num_members_input;

    ScreenController controlledScreen;
    GroupService service;

    public GroupsController() throws SQLException {
        service = new GroupService();
        groups_table = new TableView<Group>();
    }

    void fillTable() {
        age_max_col.setCellValueFactory(
                new PropertyValueFactory<>("ageMax"));
        age_min_col.setCellValueFactory(
                new PropertyValueFactory<>("ageMin"));
        age_max_col.setCellFactory(TextFieldTableCell.<Group, Integer>forTableColumn(new IntegerStringConverter()));
        age_min_col.setCellFactory(TextFieldTableCell.<Group, Integer>forTableColumn(new IntegerStringConverter()));

        section_name_col.setCellValueFactory(
                new PropertyValueFactory<>("sectionName"));

        free_places_col.setCellValueFactory(
                new PropertyValueFactory<>("maxMemberNum"));
        teacher_col.setCellValueFactory(
                new PropertyValueFactory<>("managerName"));
        free_places_col.setCellFactory(TextFieldTableCell.<Group, Integer>forTableColumn(new IntegerStringConverter()));


        groups_table.setItems(service.getAllGroups());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
        back_img.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int getBack = activeUser;
                switch (getBack) {
                    case 0:
                        controlledScreen.setScreen(ScreensFramework.screenAManage);
                        break;
                    case 1:
                        controlledScreen.setScreen(ScreensFramework.screenMHome);
                        break;
                    case 2:
                        controlledScreen.setScreen(ScreensFramework.screenTHome);
                        break;
                    default:
                        controlledScreen.setScreen(ScreensFramework.screenMainID);
                }
            }
        });
        delete_group_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = groups_table.getSelectionModel().getSelectedIndex();
                Group group = groups_table.getItems().get(index);
                service.delete(group.getGroupId());
                groups_table.getItems().remove(index);

            }
        });
        done_group_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                groups_table.setEditable(false);
                done_group_btn.setDisable(true);

            }
        });
        edit_group_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                groups_table.setEditable(true);
                done_group_btn.setDisable(false);

            }
        });


        age_max_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Group, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Group, Integer> t) {
                        ((Group) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAgeMax(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getGroupId();
                        service.updateMaxAge(id, t.getNewValue());
                    }
                }
        );

        age_min_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Group, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Group, Integer> t) {
                        ((Group) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAgeMin(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getGroupId();
                        service.updateMinAge(id, t.getNewValue());
                    }
                }
        );
        free_places_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Group, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Group, Integer> t) {
                        ((Group) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMaxMemberNum(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getGroupId();
                        service.updateMaxMemberNum(id, t.getNewValue());
                    }
                }
        );
        create_group_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(age_max_input.getText().trim().isEmpty()
                        || age_min_input.getText().isEmpty()
                        ||sections_box_input.getEditor().getText().isEmpty()
                        ||max_num_members_input.getText().trim().isEmpty()
                        ){
                    controlledScreen.alert(Alert.AlertType.WARNING,"Can not creat Group","Fill all fields");
                }
                else {
                  //логика с чек боксом
                }
            }
        });
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        controlledScreen = screenPage;
    }
}
