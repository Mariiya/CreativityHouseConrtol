package sample.controllers.admincontrollers.TabControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import sample.controllers.main.ScreenController;
import sample.model.Employee;
import sample.service.StaffService;

import java.io.IOException;
import java.sql.SQLException;

import static sample.controllers.main.AuthorizationController.activeUser;

public class StaffTabController {
    @FXML
    private Button edit_btn,done_btn,delete_btn,add_emp_btn;
    @FXML
    private TableColumn<Employee, String> position_col,spec_col,phone_col,f_name_col,last_name_col;
    @FXML
    private TableColumn<Employee, Integer> emp_id_col, max_h_col;
    @FXML
    private TableView<Employee> staff_table;

    private ScreenController screenController;
    private StaffService service;


    public StaffTabController() throws SQLException {
        screenController = new ScreenController();
        service = new StaffService();
        staff_table = new TableView<>();
        staff_table.setEditable(false);
    }

    void fillTable() {


        emp_id_col.setCellValueFactory(
                new PropertyValueFactory<>("employeeId"));
       f_name_col.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        last_name_col.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
       max_h_col.setCellValueFactory(
                new PropertyValueFactory<>("maxHours"));
       phone_col.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
       position_col.setCellValueFactory(
                new PropertyValueFactory<>("position"));
        spec_col.setCellValueFactory(
                new PropertyValueFactory<>("specialization"));

        last_name_col.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        max_h_col.setCellFactory(TextFieldTableCell.<Employee, Integer>forTableColumn(new IntegerStringConverter()));
        phone_col.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        position_col.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        spec_col.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
        staff_table.setItems(service.getAllEmployees());
    }

    @FXML
    public void initialize() {

        fillTable();

        delete_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = staff_table.getSelectionModel().getSelectedIndex();
                Employee emp = staff_table.getItems().get(index);
                service.delete(emp.getEmployeeId());
                staff_table.getItems().remove(index);
                ScreenController.setNewAction(activeUser.getId(),"Сотрудник  "+emp.getLastName()+" " +emp.getFirstName()+" удален");
            }
        });
        done_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                staff_table.setEditable(false);
                done_btn.setDisable(true);

            }
        });
        edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                staff_table.setEditable(true);
                done_btn.setDisable(false);

            }
        });

        last_name_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getEmployeeId();
                        service.updateLastName(id, t.getNewValue());
                    }
                }
        );
       phone_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPhone(t.getNewValue());
                        if (!t.getNewValue().matches(
                                "(\\d{11})?")) {
                            screenController.alert(Alert.AlertType.ERROR, "Wrong number", "Number should be 11 digits");
                        } else {
                            int id = t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).getEmployeeId();
                            service.updatePhone(id, t.getNewValue());
                        }
                    }
                }
        );
       max_h_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Employee, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Employee, Integer> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMaxHours(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getEmployeeId();
                        service.updateMaxWHours(id, t.getNewValue());
                    }
                }
        );

        spec_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSpecialization(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getEmployeeId();
                        service.updateSpecialization(id, t.getNewValue());
                    }
                }
        );

       position_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                        ((Employee) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPosition(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getEmployeeId();
                        service.updatePosition(id, t.getNewValue());
                    }
                }
        );

      add_emp_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/view/new_employee_view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 482, 532);
                    Stage stage = new Stage();
                    stage.setScene(scene);   stage.setTitle("New Employee");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(
                            ((Node)event.getSource()).getScene().getWindow() );
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
