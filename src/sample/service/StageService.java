package sample.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageService {

    public void loadStage(String viewPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource(viewPath)));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stageNew = new Stage();
        stageNew.setScene(new Scene(root));
        stageNew.showAndWait();
    }
}
