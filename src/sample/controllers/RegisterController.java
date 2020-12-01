package sample.controllers;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.controllers.main.ControlledScreen;
import sample.controllers.main.ScreenController;
import sample.controllers.main.ScreensFramework;
import sample.model.Group;
import sample.model.Register;
import sample.model.Section;
import sample.service.GroupService;
import sample.service.RegisterService;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static sample.controllers.main.AuthorizationController.activeUser;
import static sample.controllers.main.AuthorizationController.activeUserType;

public class RegisterController implements Initializable, ControlledScreen {
    @FXML
    private Button presence_done, ok_btn, view_previous_btn;
    @FXML
    private ComboBox<String> group_list;
    @FXML
    private TableView<Register> register_table;
    @FXML
    private TableColumn<Register, String> num_col;
    @FXML
    private ImageView dack_img;
    @FXML
    private TableColumn<Register, String> member_col;
    @FXML
    private TableColumn<Register, Boolean> presence_col;
    @FXML
    private DatePicker date_select;

    ScreenController controller;

    private ScreenController screenController;
    private RegisterService service;
    private GroupService groupService;
    ObservableList<Group> groups;
    List<String> groupsNames = new ArrayList<>();
    ObservableList<Integer> presence;
    LocalDateTime now = LocalDateTime.now();
    ObservableList<Register> registerSave;

    public RegisterController() {
        service = new RegisterService();
        groupService = new GroupService();
        register_table = new TableView<>();
        groups = groupService.getAllGroups();
        for (int i = 0; i < groups.size(); i++) {
            groupsNames.add(groups.get(i).getSectionName());
        }
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(ChronoLocalDate.from(now))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    void fillTable(int group_id) {
        num_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Register, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Register, String> p) {
                return new ReadOnlyObjectWrapper(register_table.getItems().indexOf(p.getValue()) + 1 + "");
            }
        });

        num_col.setSortable(false);

        member_col.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        presence_col.setCellValueFactory(new PropertyValueFactory<>("onLesson"));
        presence_col.setCellFactory(tc -> new CheckBoxTableCell<>());
        registerSave = service.getMembersByGroupId(group_id);
        register_table.setItems(registerSave);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date_select.setDayCellFactory(getDayCellFactory());
        register_table.setEditable(true);
        group_list.setItems(FXCollections.observableArrayList(groupsNames));
        ok_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = -1;
                index = group_list.getSelectionModel().getSelectedIndex();
                if (index == -1) {
                    screenController.alert(Alert.AlertType.WARNING, "no group", "select group from the list");
                } else {
                    fillTable(groups.get(index).getGroupId());
                }
                presence_done.setDisable(false);
            }
        });


        presence_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Register, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Register, Boolean> t) {
                ((Register) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setOnLesson(t.getNewValue());
            }
        });
        dack_img.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (activeUserType) {
                    case 1:
                        dack_img.setOnMouseReleased(e -> controller.setScreen(ScreensFramework.screenAManage));
                        break;
                    case 2:
                        dack_img.setOnMouseReleased(e -> controller.setScreen(ScreensFramework.screenTHome));
                        break;
                }
            }
        });

        date_select.setConverter(new javafx.util.StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                date_select.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        presence_done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (date_select.getValue().toString().isEmpty()) {
                    screenController.alert(Alert.AlertType.WARNING, "No date", "Select date");
                } else {
                    for (int i = 0; i < registerSave.size(); i++) {
                        Register r = registerSave.get(i);
                        if (service.create(r.getMemberId(), r.getGroupId(),
                                date_select.getValue().getDayOfWeek().toString(),
                                date_select.getValue().toString(), r.getOnLesson()) == -1) {
                            screenController.alert(Alert.AlertType.ERROR, "Error", "Error during adding");
                        }
                    }
                    screenController.alert(Alert.AlertType.INFORMATION, "OK", "All members saved in register");
                }
            }
        });
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.controller = screenPage;
    }
}
