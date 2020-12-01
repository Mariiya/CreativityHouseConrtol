package sample.controllers.admincontrollers.TabControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.controllers.ActionsTableController;
import sample.controllers.main.ScreenController;
import sample.model.Event;
import sample.service.EventService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

import static sample.controllers.main.AuthorizationController.activeUser;


public class EventsTabController {

    @FXML
    private TextField adress_input, time_input, name_input;
    @FXML
    private Button done_btn, view_actions_btn, delete_btn, add_btn, edit_btn;
    @FXML
    private TableColumn<Event, Integer> id_col;
    @FXML
    private DatePicker date_input;
    @FXML
    private TableView<Event> events_table;
    @FXML
    private TableColumn<Event, String> name_col, address_col, date_col, time_col;

    private ScreenController screenController;
    private EventService service;
    LocalDateTime now = LocalDateTime.now();

    public EventsTabController() throws SQLException {
        screenController = new ScreenController();
        service = new EventService();
        events_table = new TableView<Event>();
    }

    void fillTable() {
        id_col.setCellValueFactory(
                new PropertyValueFactory<>("eventId"));

        address_col.setCellValueFactory(
                new PropertyValueFactory<>("eventAddr"));
        address_col.setCellFactory(TextFieldTableCell.<Event>forTableColumn());

        date_col.setCellValueFactory(
                new PropertyValueFactory<>("eventDate"));
        date_col.setCellFactory(TextFieldTableCell.<Event>forTableColumn());

        name_col.setCellValueFactory(
                new PropertyValueFactory<>("eventName"));
        name_col.setCellFactory(TextFieldTableCell.<Event>forTableColumn());

        time_col.setCellValueFactory(
                new PropertyValueFactory<>("time"));
        time_col.setCellFactory(TextFieldTableCell.<Event>forTableColumn());

        events_table.setItems(service.getEventList());
    }


    private Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(ChronoLocalDate.from(now))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    @FXML
    public void initialize() {
        date_input.setDayCellFactory(getDayCellFactory());
        date_input.setConverter(new javafx.util.StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                date_input.setPromptText(pattern.toLowerCase());
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
        addTextLimiter(name_input, 50);
        addTextLimiter(adress_input, 50);
        fillTable();

        delete_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = events_table.getSelectionModel().getSelectedIndex();
                Event e = events_table.getItems().get(index);
                service.delete(e.getEventId());
                events_table.getItems().remove(index);
                ScreenController.setNewAction(activeUser.getId(),"Мероприятие "+e.getEventName()+"удалено");
            }
        });
        done_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                events_table.setEditable(false);
                done_btn.setDisable(true);

            }
        });
        edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                events_table.setEditable(true);
                done_btn.setDisable(false);
            }
        });

        name_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Event, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Event, String> t) {
                        ((Event) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEventName(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getEventId();
                        service.updateName(id, t.getNewValue());
                    }
                }
        );

        date_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Event, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Event, String> t) {
                        Event e = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        if (!t.getNewValue().matches(
                                "^(19|20)\\d\\d-([01][012])-([0-3][0-9])")) {
                            screenController.alert(Alert.AlertType.ERROR, "Wrong date", "Date type should be in yyyy-mm-dd");
                        } else {
                            e.setEventDate(t.getNewValue());
                            int id = t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).getEventId();
                            service.updateDate(id, t.getNewValue());
                        }
                    }
                }
        );

        address_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Event, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Event, String> t) {
                        ((Event) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setevEntAddr(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getEventId();
                        service.updateAddr(id, t.getNewValue());
                    }
                }
        );
        time_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Event, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Event, String> t) {
                        Event e = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        if (!t.getNewValue().matches("([12][0-9]):([0-5][0-9]):([0-5][0-9])")) {
                            screenController.alert(Alert.AlertType.ERROR, "Wrong time", "Time should be in 10:00:00-20:00:00");
                        } else {
                            e.setTime(t.getNewValue());
                            int id = t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).getEventId();
                            service.updateTime(id, t.getNewValue());
                        }
                    }
                }
        );

        add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = name_input.getText().trim();
                String addr = adress_input.getText().trim();
                String time = time_input.getText().trim();
                String date = date_input.getValue().toString();
                if (name.isEmpty() || addr.isEmpty() || time.isEmpty() || date.isEmpty()) {
                    screenController.alert(Alert.AlertType.WARNING, "Empty field", "Fill all fields");
                } else {
                    if (service.create(time, name, date, addr) != -1) {
                        screenController.alert(Alert.AlertType.INFORMATION, "OK", "New event created");
                        ScreenController.setNewAction(activeUser.getId(),"Мероприятие "+name+"создано");
                    } else {
                        screenController.alert(Alert.AlertType.ERROR, "Fail", "New event creating failed");
                    }
                }
            }
        });
        view_actions_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id;
            if ( events_table.getSelectionModel().getSelectedItem()!=null){
                id=events_table.getSelectionModel().getSelectedItem().getEventId();
                ActionsTableController controller= new ActionsTableController(id);
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/sample/view/actions_by_event_id.fxml"
                        )
                );
                loader.setController(controller);
                Scene scene = null;
                try {
                    scene = new Scene(loader.load(), 600, 400);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node)event.getSource()).getScene().getWindow() );
                stage.showAndWait();
                } else {
                    screenController.alert(Alert.AlertType.WARNING, "Fail", "Select event row");
                }
            }
            });
        }
    }
