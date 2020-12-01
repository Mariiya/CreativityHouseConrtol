package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;
import sample.controllers.main.ControlledScreen;
import sample.controllers.main.ScreenController;
import sample.controllers.main.ScreensFramework;
import sample.model.Group;
import sample.service.GroupService;
import sample.service.LessonService;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import static sample.controllers.main.AuthorizationController.activeUser;
import static sample.controllers.main.AuthorizationController.activeUserType;

public class GroupsController implements ControlledScreen {
    @FXML
    private Pane create_group_pane;

    @FXML
    private ImageView back_img;
    @FXML
    private ComboBox<String> sections_box_input;
    @FXML
    private TableColumn<Group, String> teacher_col, section_name_col;
    @FXML
    private Button delete_group_btn, view_members_btn, create_group_btn, done_group_btn, edit_group_btn, create_lessons_btn;
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

    HashMap<String, Integer> sections = new HashMap<String, Integer>();
    ObservableList<String> targetList;

    public GroupsController() throws SQLException {
        service = new GroupService();
        groups_table = new TableView<Group>();
        sections = service.getAllSections();
        groups_table.setEditable(true);
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
        switch (activeUserType) {
            case 1:
                groups_table.setItems(service.getAllGroups());
                break;
            case 2:
                groups_table.setItems(service.getGroupsListByManagerId(activeUser.getUserId()));
                break;
            default:
        }

    }


    @FXML
    public void initialize() {

        sections = service.getAllSections();
        Set<String> sourceSet = sections.keySet();
        targetList = FXCollections.observableArrayList(List.copyOf(sourceSet));
        sections_box_input.setItems(targetList);
        groups_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fillTable();
            }
        });

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        age_max_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) age_max_input.setText(oldValue);
        });
        age_min_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) age_min_input.setText(oldValue);
        });
        max_num_members_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) max_num_members_input.setText(oldValue);
        });

        back_img.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int getBack = activeUserType;

                switch (getBack) {
                    case 1:
                        controlledScreen.setScreen(ScreensFramework.screenAManage);
                        break;
                    case 2:
                        controlledScreen.setScreen(ScreensFramework.screenMHome);
                        break;
                    case 3:
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
                if (index > 0) {
                    Group group = groups_table.getItems().get(index);
                    service.delete(group.getGroupId());
                    groups_table.getItems().remove(index);
                }
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
                if (activeUserType == 1) {
                    create_group_pane.setDisable(true);
                    create_group_btn.setDisable(true);
                    controlledScreen.alert(Alert.AlertType.WARNING, "Admin can not create groups", "Autorise as a teacher");
                } else {
                    if (age_max_input.getText().trim().isEmpty()
                            || age_min_input.getText().isEmpty()
                            || sections_box_input.getValue().isEmpty()
                            || max_num_members_input.getText().trim().isEmpty()
                    ) {
                        controlledScreen.alert(Alert.AlertType.WARNING, "Can not creat Group", "Fill all fields");
                    } else {
                        if (service.create(Integer.parseInt(age_min_input.getText()), Integer.parseInt(age_max_input.getText()), Integer.parseInt(max_num_members_input.getText()),
                                activeUser.getUserId(), sections.get(sections_box_input.getValue())) != -1)
                            controlledScreen.alert(Alert.AlertType.INFORMATION, "Group", "New Group created!");
                    }
                }
            }
        });

        create_lessons_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id;
                if (groups_table.getSelectionModel().getSelectedItem() == null) {
                    controlledScreen.alert(Alert.AlertType.WARNING, "Fail", "Select group row");
                } else {
                    id = groups_table.getSelectionModel().getSelectedItem().getGroupId();
                    String mon, tue, wed, thu, fri, sat, sun;
                    mon = monday_dur_input.getText().trim();
                    tue = tue_dur_input.getText().trim();
                    wed = wed_dur_input.getText().trim();
                    thu = thu_dur_input.getText().trim();
                    fri = fri_dur_input.getText().trim();
                    sat = sat_dur_input.getText().trim();
                    sun = sun_dur_input.getText().trim();

                    if (!sat_check_box.isSelected()
                            && !monday_check_box.isSelected()
                            && !tue_check_box.isSelected()
                            && !wed_check_box.isSelected()
                            && !fri_check_box.isSelected()
                            && !thu_check_box.isSelected()
                            && !sun_check_box.isSelected()
                            && (
                            mon.isEmpty()
                                    && tue.isEmpty()
                                    && wed.isEmpty()
                                    && thu.isEmpty()
                                    && fri.isEmpty()
                                    && sat.isEmpty()
                                    && sun.isEmpty()
                    )
                    ) {
                        controlledScreen.alert(Alert.AlertType.WARNING, "Can not creat Lessons", "Select day(s) of week\n and duration");
                    } else {
                        LessonService lessonService = new LessonService();
                        if (monday_check_box.isSelected() && !mon.isEmpty())
                            lessonService.create(1, id, monday_time_input.getText(), Integer.parseInt(monday_dur_input.getText()));
                        if (tue_check_box.isSelected() && !thu.isEmpty())
                            lessonService.create(2, id, tue_time_input.getText(), Integer.parseInt(tue_dur_input.getText()));
                        if (wed_check_box.isSelected() && !wed.isEmpty())
                            lessonService.create(3, id, wed_time_input.getText(), Integer.parseInt(wed_dur_input.getText()));
                        if (thu_check_box.isSelected() && !thu.isEmpty())
                            lessonService.create(4, id, thu_time_input.getText(), Integer.parseInt(thu_dur_input.getText()));
                        if (fri_check_box.isSelected() && !fri.isEmpty())
                            lessonService.create(5, id, fri_time_input.getText(), Integer.parseInt(fri_dur_input.getText()));
                        if (sat_check_box.isSelected() && !sat.isEmpty())
                            lessonService.create(6, id, sat_time_input.getText(), Integer.parseInt(sat_dur_input.getText()));
                        if (sun_check_box.isSelected() && !sun.isEmpty())
                            lessonService.create(7, id, sun_time_input.getText(), Integer.parseInt(sun_dur_input.getText()));
                        controlledScreen.alert(Alert.AlertType.INFORMATION, "Section", "New Lessons created!");
                        ScreenController.setNewAction(activeUser.getUserId(), "Новый урок(и)  у группы" + groups_table.getSelectionModel().getSelectedItem().getSectionName() + " создан");
                    }
                }
            }
        });

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        controlledScreen = screenPage;
    }
}
