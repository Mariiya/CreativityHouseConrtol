package sample.controllers.TabControllers;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.controllers.ScreenController;
import sample.model.Payment;
import sample.model.Section;
import sample.service.PaymentService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static sample.controllers.AuthorizationController.activeUser;

public class PaymentTabController {
    @FXML
    private TableColumn<Payment, String> pref_cat_col, section_col, member_col, pay_date_col;
    @FXML
    private TextField last_name_input, first_name_input, phone_input;
    @FXML
    private TableColumn<Payment, Integer> id_col;
    @FXML
    private Button apply_btn, add_btn, enter_btn;
    @FXML
    private DatePicker payments_to_date, payments_from_date, payment_date;
    @FXML
    private TableColumn<?, ?> amount_col;
    @FXML
    private CheckBox pref_cat_input;
    @FXML
    private ComboBox<String> section_input;
    @FXML
    private TextField amount_input;
    @FXML
    private TableView<Payment> payments_table;

    private ScreenController screenController;
    private PaymentService service;
    List<String> sections = new ArrayList<>();
    ObservableList<String> targetList;
    LocalDateTime now = LocalDateTime.now();
    int member_id, group_id;
    String pref_cat;
    List<Payment> list;
    double amountPayment;

    public PaymentTabController() throws SQLException {
        screenController = new ScreenController();
        service = new PaymentService();
        payments_table = new TableView<Payment>();
        payments_table.setEditable(false);
    }

    void fillTable() {
        id_col.setCellValueFactory(
                new PropertyValueFactory<>("paymentId"));
        pay_date_col.setCellValueFactory(
                new PropertyValueFactory<>("paymentDate"));
        amount_col.setCellValueFactory(
                new PropertyValueFactory<>("amount"));
        pref_cat_col.setCellValueFactory(
                new PropertyValueFactory<>("pref_category"));
        member_col.setCellValueFactory(
                new PropertyValueFactory<>("memberName"));
        section_col.setCellValueFactory(
                new PropertyValueFactory<>("sectionName"));
        payments_table.setItems(service.getPaymentList());
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

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        phone_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) phone_input.setText(oldValue);
        });

        Pattern amount = Pattern.compile("(\\d+\\d*)?");
        amount_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!amount.matcher(newValue).matches()) amount_input.setText(oldValue);
        });


        payment_date.setDayCellFactory(getDayCellFactory());
        payment_date.setConverter(new javafx.util.StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);


            {
                payment_date.setPromptText(pattern.toLowerCase());
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

        enter_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fname = first_name_input.getText().trim();
                String lname = last_name_input.getText().trim();
                String phone = phone_input.getText().trim();
                if (fname.isEmpty() || lname.isEmpty() || phone.isEmpty()) {
                    ScreenController.alert(Alert.AlertType.ERROR, "No member", "Member not found!");
                } else {

                }
            }
        });

        addTextLimiter(phone_input, 12);
        addTextLimiter(first_name_input, 30);
        addTextLimiter(last_name_input, 30);
        fillTable();

        apply_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate from, to;

                from = payments_from_date.getValue();
                to = payments_to_date.getValue();
                if (from.toString().isEmpty() || to.toString().isEmpty()) {
                    screenController.alert(Alert.AlertType.WARNING, "Date not selected", "Select date");
                } else {
                    payments_table.setItems(service.getPaymentListByDate(from, to));
                }
            }
        });

        enter_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fname, sname, phone;
                fname = first_name_input.getText();
                sname = last_name_input.getText();
                phone = phone_input.getText();
                list = service.getGroupsForMember(fname, sname, phone);
                if (fname.trim().isEmpty()
                        || sname.trim().isEmpty()
                        || phone.trim().isEmpty()
                        || list.size() < 1) {
                    screenController.alert(Alert.AlertType.WARNING, "No such user", "Can not find member,check your data");
                } else {
                    member_id = list.get(0).getMemberId();
                    List<String> listItems = new ArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        listItems.add(list.get(i).getSectionName());
                        pref_cat = list.get(i).getPref_category();
                    }
                    section_input.setItems(FXCollections.observableArrayList(listItems));
                    section_input.setDisable(false);
                    pref_cat_input.setDisable(false);
                    payment_date.setDisable(false);
                    add_btn.setDisable(false);
                }
            }
        });
        add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = section_input.getSelectionModel().getSelectedIndex();
                amountPayment = Double.parseDouble(amount_input.getText());
                if (index < 0) {
                    screenController.alert(Alert.AlertType.WARNING, "No group", "Select group for payment");
                } else {
                    if (amountPayment == 0) {
                        screenController.alert(Alert.AlertType.WARNING, "Wrong amount", "Amount can not be 0");
                    } else {
                        group_id = list.get(index).getGroupId();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure in your data? \n You could not change it!");
                        Optional<ButtonType> option = alert.showAndWait();
                        if (option.get() == ButtonType.OK) {
                            if (service.create(payment_date.getValue().toString(), amountPayment, pref_cat, member_id, group_id) != -1) {
                                screenController.alert(Alert.AlertType.INFORMATION, "Payment", "New Payment created!");
                                ScreenController.setNewAction(activeUser.getUserId(),"Новая оплата за "
                                        +section_input.getValue()+" оплачена " +last_name_input);
                                first_name_input.clear();
                                last_name_input.clear();
                                phone_input.clear();

                            }
                        }
                    }
                }
            }
        });

    }

}
