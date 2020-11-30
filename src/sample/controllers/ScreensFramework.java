package sample.controllers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ScreensFramework extends Application {

    public static String screenMainID="main";
    public static String screenMainFile="/sample/view/autorisationView.fxml";

    public static String screenAHomeID="adminHomePage";
    public static String screenAHomeFile="/sample/view/admin_home_page.fxml";

    public static String screenAManage="AManage";
    public static String screenAManageFile="/sample/view/admin_manage_page.fxml";

    public static String screenTimeTable="TT";
    public static String screenTTFile="/sample/view/time_table_view.fxml";

    public static String screenMHome="memberHomePage";
    public static String screenMHomeFile="/sample/view/member_home_page.fxml";

    public static String screenTHome="memberHomePage";
    public static String screenTHomeFile="/sample/view/teacher_home_page.fxml";

    public static String screenGroups="groups";
    public static String screenGroupFile="/sample/view/groups_view.fxml";

    public static String screenRegister="register";
    public static String screenRegisterFile="/sample/view/register_view.fxml";

    public static String screenReports="reports";
    public static String screenReportsFile="/sample/view/reports_view.fxml";
    public static String screenHistory="history";
    public static String screenHistoryFile="/sample/view/users_histoty.fxml";
    @Override
    public void start(Stage primaryStage) throws Exception {

        ScreenController mainContainer =new ScreenController();
        mainContainer.loadScreen(ScreensFramework.screenMainID,screenMainFile);
        mainContainer.loadScreen(ScreensFramework.screenAHomeID,screenAHomeFile);
        mainContainer.loadScreen(ScreensFramework.screenAManage,screenAManageFile);
        mainContainer.loadScreen(ScreensFramework.screenTimeTable,screenTTFile);
        mainContainer.loadScreen(ScreensFramework.screenMHome,screenMHomeFile);
        mainContainer.loadScreen(ScreensFramework.screenTHome,screenTHomeFile);
        mainContainer.loadScreen(ScreensFramework.screenGroups,screenGroupFile);
        mainContainer.loadScreen(ScreensFramework.screenRegister,screenRegisterFile);
        mainContainer.loadScreen(ScreensFramework.screenReports,screenReportsFile);
      //  mainContainer.loadScreen(ScreensFramework.screenHistory,screenHistoryFile);

        mainContainer.setScreen(ScreensFramework.screenMainID);

        Group root=new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
