package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import sample.model.Group;
import sample.service.GroupService;
import sample.service.LessonService;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import static sample.controllers.AuthorizationController.activeUser;
import static sample.controllers.AuthorizationController.activeUserType;

public class GroupsController implements Initializable, ControlledScreen {
    @FXML
    private ImageView back_img;
    @FXML
    private ComboBox<String> sections_box_input;
    @FXML
    private TableColumn<Group, String> teacher_col, section_name_col;
    @FXML
    private Button delete_group_btn, view_members_btn, create_group_btn, done_group_btn, edit_group_btn;
    @FXML
    private CheckBox monday_check_box, tue_check_box, fri_check_box, sat_check_box, wed_check_box, sun_check_box, thu_check_box;
    @FXML
    private TableColumn<Group, Integer> age_min_col, age_max_col, free_places_col;
    @FXML
    private TableView<Group> groups_table;
    @FXML
    private TextField monday_time_input, age_max_input, sun_time_input, thu_time_input, fri_time_input, sat_time_input, wed_time_input, tue_time_input, age_min_input, max_num_members_input;
    @FXML
    private TextField fri_dur_input, monday_dur_input, tue_dur_input, wed_dur_input, thu_dur_input, sat_dur_input, sun_dur_input;
    ScreenController controlledScreen;
    GroupService service;
    boolean allowCreateLessons = false;

    HashMap<String, Integer> sections = new HashMap<String, Integer>();
    ObservableList<String> targetList;

    public GroupsController() throws SQLException {
        service = new GroupService();
        groups_table = new TableView<Group>();
        sections = service.getAllSections();
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

        free_places_col.setCellValueFactory(
                new PropertyValueFactory<>("maxMemberNum"));
        if(activeUserType==0) {
            groups_table.setItems(service.getAllGroups());
        }
        else {
            groups_table.setItems(service.getGroupsListByManagerId(activeUser.getUserId()));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sections = service.getAllSections();
        Set<String> sourceSet = sections.keySet();
        targetList = FXCollections.observableArrayList(List.copyOf(sourceSet));
        fillTable();
        sections_box_input.setItems(targetList);

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        age_max_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) age_max_input.setText(oldValue);
        });
        age_min_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) age_min_input.setText(oldValue);
        });
        max_num_members_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches())  max_num_members_input.setText(oldValue);
        });

        back_img.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int getBack = activeUserType;
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

                if (age_max_input.getText().trim().isEmpty()
                        || age_min_input.getText().isEmpty()
                        || sections_box_input.getValue().toString().isEmpty()
                        || max_num_members_input.getText().trim().isEmpty()
                ) {
                    controlledScreen.alert(Alert.AlertType.WARNING, "Can not creat Group", "Fill all fields");
                } else {
                    if (
                            service.create(Integer.parseInt(age_min_input.getText()), Integer.parseInt(age_max_input.getText()), Integer.parseInt(max_num_members_input.getText()),
                                    activeUser.getUserId(),  sections.get(sections_box_input.getValue()))
                    ) {
                        controlledScreen.alert(Alert.AlertType.INFORMATION, "Section", "New Section created!");
                        allowCreateLessons = true;
                    }
                }
                if (!sat_check_box.isSelected()
                        && !monday_check_box.isSelected()
                        && !tue_check_box.isSelected()
                        && !wed_check_box.isSelected()
                        && !fri_check_box.isSelected()
                        && !thu_check_box.isSelected()
                        && !sun_check_box.isSelected()
                ) {
                    controlledScreen.alert(Alert.AlertType.WARNING, "Can not creat Lessons", "Select day(s) of week");
                    allowCreateLessons = false;
                } else {
                    int groupId = service.getLastAddedGroup();
                    if (allowCreateLessons && groupId > 0) {
                        LessonService lessonService = new LessonService();
                        if (monday_check_box.isSelected())
                            lessonService.create(1, groupId, monday_time_input.getText(), Integer.parseInt(monday_dur_input.getText()));
                        if (tue_check_box.isSelected())
                            lessonService.create(2, groupId, tue_time_input.getText(), Integer.parseInt(tue_dur_input.getText()));
                        if (wed_check_box.isSelected())
                            lessonService.create(3, groupId, wed_time_input.getText(), Integer.parseInt(wed_dur_input.getText()));
                        if (thu_check_box.isSelected())
                            lessonService.create(4, groupId, thu_time_input.getText(), Integer.parseInt(thu_dur_input.getText()));
                        if (fri_check_box.isSelected())
                            lessonService.create(5, groupId, fri_time_input.getText(), Integer.parseInt(fri_dur_input.getText()));
                        if (sat_check_box.isSelected())
                            lessonService.create(6, groupId, sat_time_input.getText(), Integer.parseInt(sat_dur_input.getText()));
                        if (sun_check_box.isSelected())
                            lessonService.create(7, groupId, sun_time_input.getText(), Integer.parseInt(sun_dur_input.getText()));
                        controlledScreen.alert(Alert.AlertType.INFORMATION, "Section", "New Lessons created!");
                    }
                    controlledScreen.alert(Alert.AlertType.INFORMATION, "Section", "Error during creating group");
                }
            }
        });
    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
        controlledScreen = screenPage;
    }
}
