package sample.controllers;

import javafx.fxml.Initializable;
import sample.controllers.main.ControlledScreen;
import sample.controllers.main.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberHomePageController implements Initializable, ControlledScreen {

    ScreenController controller;

    public MemberHomePageController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.controller = screenPage;
    }
}
