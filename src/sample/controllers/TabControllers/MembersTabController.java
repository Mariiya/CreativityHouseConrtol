package sample.controllers.TabControllers;

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
import sample.controllers.ScreenController;
import sample.model.Member;
import sample.service.MemberService;
import java.io.IOException;
import java.sql.SQLException;

import static sample.controllers.AuthorizationController.activeUser;

public class MembersTabController {
    @FXML
    private TableColumn<Member, String> med_date_col, member_birth_date_col;
    @FXML
    private TableColumn<Member, String> parent_name_col, member_name_col, member_surname_col, parent_phone_col, member_id_col;
    @FXML
    private TableView<Member> members_table;
    @FXML
    private Button add_member_btn, delete_member_btn, edit_member_btn, done_member_btn;

    private ScreenController screenController;
    private MemberService service;


    public MembersTabController() throws SQLException {
        screenController = new ScreenController();
        service = new MemberService();
        members_table = new TableView<>();
        members_table.setEditable(false);
    }

    void fillTable() {
        med_date_col.setCellValueFactory(
                new PropertyValueFactory<>("medicalCertificateDate"));
        member_birth_date_col.setCellValueFactory(
                new PropertyValueFactory<>("birthDate"));
        member_id_col.setCellValueFactory(
                new PropertyValueFactory<>("birthCertificate"));
        member_name_col.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        member_surname_col.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        parent_phone_col.setCellValueFactory(
                new PropertyValueFactory<>("parentPhone"));
        parent_name_col.setCellValueFactory(
                new PropertyValueFactory<>("parentName"));

        med_date_col.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
        member_surname_col.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
        parent_phone_col.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
        parent_name_col.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
        members_table.setItems(service.getAllMembers());
    }

    @FXML
    public void initialize() {

        fillTable();

        delete_member_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = members_table.getSelectionModel().getSelectedIndex();
                Member memeber = members_table.getItems().get(index);
                service.delete(memeber.getMemberId());
                members_table.getItems().remove(index);
                ScreenController.setNewAction(activeUser.getId(),"Учасник "+memeber.getLastName()+ " "+ memeber.getFirstName()+"удален");
            }
        });
        done_member_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                members_table.setEditable(false);
                done_member_btn.setDisable(true);

            }
        });
        edit_member_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                members_table.setEditable(true);
                done_member_btn.setDisable(false);

            }
        });

        med_date_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Member, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Member, String> t) {
                        ((Member) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMedicalCertificateDate(t.getNewValue());
                        if (!t.getNewValue().matches(
                                "^(19|20)\\d\\d-([01][012])-([0-3][0-9])")) {
                            screenController.alert(Alert.AlertType.ERROR, "Wrong date", "Date type should be in yyyy-mm-dd");
                        } else {
                            int id = t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).getMemberId();
                            service.updateMedCertificate(id, t.getNewValue());
                        }
                    }
                }
        );
        member_surname_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Member, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Member, String> t) {
                        ((Member) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getMemberId();
                        service.updateLastName(id, t.getNewValue());
                    }
                }
        );
        parent_phone_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Member, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Member, String> t) {
                        ((Member) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setParentPhone(t.getNewValue());
                        if (!t.getNewValue().matches(
                                "(\\d{11})?")) {
                            screenController.alert(Alert.AlertType.ERROR, "Wrong number", "Number should be 11 digits");
                        } else {
                            int id = t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).getMemberId();
                            service.updatePhoneNumber(id, t.getNewValue());
                        }
                    }
                }
        );
        parent_name_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Member, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Member, String> t) {
                        ((Member) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setParentName(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getMemberId();
                        service.updateParentName(id, t.getNewValue());
                    }
                }
        );

        add_member_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/view/member_create_view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 482, 532);
                    Stage stage = new Stage();
                    stage.setScene(scene);   stage.setTitle("New Member");
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
