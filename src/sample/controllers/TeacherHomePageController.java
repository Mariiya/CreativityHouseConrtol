package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
         requests_btn.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 try {
                     FXMLLoader fxmlLoader = new FXMLLoader();
                     fxmlLoader.setLocation(getClass().getResource("/sample/view/request_view.fxml"));
                     Scene scene = new Scene(fxmlLoader.load(), 749, 379);
                     Stage stage = new Stage();
                     stage.setScene(scene);   stage.setTitle("Requests");
                     stage.initModality(Modality.WINDOW_MODAL);
                     stage.initOwner(
                             ((Node)event.getSource()).getScene().getWindow() );
                     stage.showAndWait();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
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
