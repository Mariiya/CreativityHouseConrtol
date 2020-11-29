package sample.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.model.Lessons;
import sample.model.TimeTable;
import sample.service.LessonService;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeTableController implements Initializable, ControlledScreen {

    @FXML
    private TableColumn<TimeTable, String> group_col, tue_col, sun_col, sat_col, mon_col, fri_col, thu_col, wed_col;
    @FXML
    private TableView<TimeTable> tt_table;
    private ScreenController screenController;
    private LessonService service;

    public TimeTableController() {
        service = new LessonService();
        tt_table = new TableView<>();
    }

    void fillTable() {
        group_col.setCellValueFactory(
                new PropertyValueFactory<>("group"));
        mon_col.setCellValueFactory(
                new PropertyValueFactory<>("mon"));
       tue_col.setCellValueFactory(
                new PropertyValueFactory<>("tue"));
        wed_col.setCellValueFactory(
                new PropertyValueFactory<>("wed"));
        thu_col.setCellValueFactory(
                new PropertyValueFactory<>("thu"));
        fri_col.setCellValueFactory(
                new PropertyValueFactory<>("fri"));
        sat_col.setCellValueFactory(
                new PropertyValueFactory<>("sat"));
        sun_col.setCellValueFactory(
                new PropertyValueFactory<>("sun"));
        
        tt_table.setItems(service.getGroupTimeTable());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

}
