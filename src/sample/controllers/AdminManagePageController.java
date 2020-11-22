package sample.controllers;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.Lesson;

import java.sql.Time;

public class AdminManagePageController {

        @FXML
        private TableColumn<Lesson, Time> lessons_sat_col;
        @FXML
        private Button reports_btn;
        @FXML
        private TableColumn<Lesson, Time> lessons_fri_col;
        @FXML
        private Button time_table_btn;
        @FXML
        private Button lessons_edit_btn;
        @FXML
        private TableColumn<Lesson, Time> lessons_mon_col;
        @FXML
        private Button groups_btn;
        @FXML
        private TableColumn<Lesson, Time> lessons_san_col;
        @FXML
        private Button lessons_delete_btn;
        @FXML
        private TableColumn<Lesson, Time> lessons_group_col;
        @FXML
        private TableColumn<Lesson, Time> lessons_thu_col;
        @FXML
        private TableView<Lesson> lessons_table;
        @FXML
        private TableColumn<Lesson, Time> lessons_tue_col;
        @FXML
        private TableColumn<Lesson, Time> lessons_wed_con;
        @FXML
        private Button register_btn;



}
