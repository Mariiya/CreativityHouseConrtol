package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherHomePageController implements Initializable,ControlledScreen{
    @FXML
    private Button time_table_btn, manage_group_btn, log_out_btn, register_btn, requests_btn, new_event_btn;

    ScreenController controller;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time_table_btn.setOnAction(e->controller.setScreen(ScreensFramework.screenTimeTable));
        manage_group_btn.setOnAction(e->controller.setScreen(ScreensFramework.screenGroups));
        log_out_btn.setOnAction(e->controller.setScreen(ScreensFramework.screenMainID));
        register_btn.setOnAction(e->controller.setScreen(ScreensFramework.screenRegister));
        // requests_btn.setOnAction(e->controller.setScreen(ScreensFramework.));
        new_event_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.controller = screenPage;
    }
}
