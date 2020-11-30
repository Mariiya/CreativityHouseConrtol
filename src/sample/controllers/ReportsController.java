package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable, ControlledScreen {
    @FXML
    private ImageView back_img;
   private  ScreenController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back_img.setOnMouseReleased(e -> controller.setScreen(ScreensFramework.screenAManage));
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.controller = screenPage;
    }
}
