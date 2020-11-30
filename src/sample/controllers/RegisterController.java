package sample.controllers;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.controllers.AuthorizationController.activeUserType;

public class RegisterController implements Initializable, ControlledScreen {

    @FXML
    private ImageView dack_img;
    ScreenController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       dack_img.setOnMouseReleased(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               switch (activeUserType){
                   case 1 : dack_img.setOnMouseReleased(e->controller.setScreen(ScreensFramework.screenAManage));
                   break;
                   case 2: dack_img.setOnMouseReleased(e->controller.setScreen(ScreensFramework.screenTHome));
                   break;
               }
           }
       });

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.controller = screenPage;
    }
}
