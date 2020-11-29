package sample.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.ImageView;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.controllers.TabControllers.SectionsTabController;
import sample.model.Lessons;
import sample.service.LessonService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AdminManagePageController implements Initializable, ControlledScreen {

    @FXML
    private Button groups_btn, register_btn, lessons_edit_btn, time_table_btn, reports_btn, lessons_delete_btn, lessons_done_btn;
    @FXML
    private TableColumn<Lessons, String> lessons_group_col, lessons_time_col, lesson_type_col, lessons_day_col;
    @FXML
    private TableColumn<Lessons, Integer> lessons_room_col, lessons_dur_col;
    @FXML
    private ImageView back_img,logo_img;
    @FXML
    private Tab section_tab;

    @FXML
    private TableView<Lessons> lessons_table;

    private ScreenController screenController;
    private SectionsTabController sectionsTabController;
    private LessonService service;
    private ObservableList<String> day;
    private ObservableList<Integer> room;

    public AdminManagePageController() throws SQLException {
        service = new LessonService();
        lessons_table = new TableView<Lessons>();
        lessons_table.setEditable(true);
        day = FXCollections.observableArrayList();
        day.add("Thursday");
        day.add("Thursday");
        day.add("Friday");
        day.add("Saturday");
        day.add("Sunday");
        room = FXCollections.observableArrayList();
        room.addAll(service.getRoomsList());
    }


    void fillTable() {
        lessons_day_col.setCellValueFactory(
                new PropertyValueFactory<>("day_of_week"));
        lessons_day_col.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), day));

        lessons_group_col.setCellValueFactory(
                new PropertyValueFactory<>("group"));

        lesson_type_col.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        lessons_time_col.setCellValueFactory(
                new PropertyValueFactory<>("time"));
        lessons_time_col.setCellFactory(TextFieldTableCell.<Lessons>forTableColumn());

        lessons_dur_col.setCellValueFactory(
                new PropertyValueFactory<>("duration"));
        lessons_dur_col.setCellFactory(TextFieldTableCell.<Lessons, Integer>forTableColumn(new IntegerStringConverter()));

        lessons_room_col.setCellValueFactory(
                new PropertyValueFactory<>("room"));
        lessons_room_col.setCellFactory(ComboBoxTableCell.forTableColumn(room));

        lessons_table.setItems(service.getTimeTable());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillTable();
        groups_btn.setOnAction(e -> screenController.setScreen(ScreensFramework.screenGroups));
        register_btn.setOnAction(e -> screenController.setScreen(ScreensFramework.screenRegister));
        time_table_btn.setOnAction(e -> screenController.setScreen(ScreensFramework.screenTimeTable));
        reports_btn.setOnAction(e -> screenController.setScreen(ScreensFramework.screenReports));
        lessons_delete_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = lessons_table.getSelectionModel().getSelectedIndex();
                Lessons tt = lessons_table.getItems().get(index);
                service.delete(tt.getGroupId(), tt.getDay_of_week());
                lessons_table.getItems().remove(index);

            }
        });
        lessons_done_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lessons_table.setEditable(false);
                lessons_done_btn.setDisable(true);

            }
        });
        lessons_edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lessons_table.setEditable(true);
                lessons_done_btn.setDisable(false);

            }
        });

        lessons_day_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Lessons, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Lessons, String> t) {
                        ((Lessons) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDay_of_week(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getGroupId();
                        service.updateWeek(id, t.getNewValue(), t.getOldValue());


                    }
                }
        );

        lessons_time_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Lessons, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Lessons, String> t) {
                        Lessons tt = (Lessons) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        if (!t.getNewValue().matches("([1][0-9]):([0-5][0-9]):([0-5][0-9])")) {
                            screenController.alert(Alert.AlertType.ERROR, "Wrong time", "Time should be in 10:00:00-19:00:00");
                        } else {
                            tt.setTime(t.getNewValue());
                            service.updateTime(tt.getGroupId(), t.getNewValue(), tt.getDay_of_week());
                        }
                    }
                }
        );

        lessons_room_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Lessons, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Lessons, Integer> t) {
                        Lessons tt = (Lessons) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        tt.setRoom(t.getNewValue());
                        service.updateRoom(tt.getGroupId(), t.getNewValue(), tt.getDay_of_week());
                    }
                }
        );


        lessons_dur_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Lessons, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Lessons, Integer> t) {
                        Lessons tt = (Lessons) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        screenController.alert(Alert.AlertType.INFORMATION, "Duration", "Duration is in minutes!");
                        tt.setDuration(t.getNewValue());
                        service.updateDuration(tt.getGroupId(), t.getNewValue(), tt.getDay_of_week());
                    }
                }
        );

    back_img.setOnMouseReleased(e-> screenController.setScreen(ScreensFramework.screenAHomeID));
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }
}
