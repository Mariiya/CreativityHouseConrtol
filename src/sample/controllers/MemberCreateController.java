package sample.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.service.MemberService;
import sample.service.UserService;

import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class MemberCreateController {

    @FXML
    private TextField birth_certificate_input;

    @FXML
    private TextField parent_name_input;

    @FXML
    private TextField first_name_input;

    @FXML
    private TextField email_input;

    @FXML
    private DatePicker medical_certificate_date;
    @FXML
    private ComboBox<Integer> mm_input, dd_input, yyyy_input;
    @FXML
    private TextField phone_input;

    @FXML
    private TextField las_name_input;
    @FXML
    Button add_member_btn;
    ScreenController screenController;
    MemberService memberService;
    UserService userService;
    Calendar dateNow;
    LocalDateTime now = LocalDateTime.now();

    public MemberCreateController() {
        screenController = new ScreenController();
        memberService = new MemberService();
        try {
            userService = new UserService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dateNow = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        dateNow.setTime(new java.util.Date());
    }


    private Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);


                        if (item.getYear() > now.getYear()
                                || item.getMonth().getValue() > now.getMonth().getValue()
                                || item.getDayOfYear() > now.getDayOfYear()) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    private ObservableList<Integer> generatorDay() {
        ObservableList<Integer> result = FXCollections.observableArrayList();
        Integer[] arr = new Integer[32];

        for (int i = 1; i < arr.length; i++) {
            arr[i - 1] = i;
        }
        result.addAll(arr);
        return result;
    }

    private ObservableList<Integer> generatorMonth() {
        ObservableList<Integer> result = FXCollections.observableArrayList();
        Integer[] arr = new Integer[13];

        for (int i = 1; i < arr.length; i++) {
            arr[i - 1] = i;
        }
        result.addAll(arr);
        return result;
    }

    private ObservableList<Integer> generatorYear() {
        ObservableList<Integer> result = FXCollections.observableArrayList();
        Integer[] arr = new Integer[dateNow.get(java.util.Calendar.YEAR) - 2000];

        for (int i = 2000; i < dateNow.get(java.util.Calendar.YEAR); i++) {
            arr[i - 2000] = i;
        }
        result.addAll(arr);
        return result;
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

        dd_input.setItems(generatorDay());
        mm_input.setItems(generatorMonth());
        yyyy_input.setItems(generatorYear());
        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        phone_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) phone_input.setText(oldValue);
        });


        addTextLimiter(phone_input, 10);
        addTextLimiter(birth_certificate_input, 10);
        addTextLimiter(las_name_input, 30);
        addTextLimiter(first_name_input, 30);
        addTextLimiter(parent_name_input, 50);
        addTextLimiter(email_input, 20);

        medical_certificate_date.setDayCellFactory(getDayCellFactory());
        medical_certificate_date.setConverter(new javafx.util.StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                medical_certificate_date.setPromptText(pattern.toLowerCase());
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


        add_member_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String birth_cert, dd, mm, yyyy, las_name, first_name, email, parent_name, parent_phone;
                birth_cert = birth_certificate_input.getText().trim();
                dd = dd_input.getValue().toString();
                mm = mm_input.getValue().toString();
                yyyy = yyyy_input.getValue().toString();
                las_name = las_name_input.getText().trim();
                first_name = first_name_input.getText().trim();
                email = email_input.getText().trim();
                parent_name = parent_name_input.getText().trim();
                parent_phone = phone_input.getText().trim();
                LocalDate med_date = medical_certificate_date.getValue();
                if (userService.isEmail(email)) {
                    ScreenController.alert(Alert.AlertType.ERROR, "Email error", "This email is already in use");
                } else {
                    if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                        screenController.alert(Alert.AlertType.WARNING, "Wrong email", "Wrong email input!");
                    } else {
                        if (now.getYear() - Integer.parseInt(yyyy) < 3) {
                            screenController.alert(Alert.AlertType.WARNING, "Can not creat new member", "Child must be over 3 years old");
                        }

                        if (birth_cert.isEmpty() || dd.isEmpty() || mm.isEmpty() || yyyy.isEmpty() || las_name.isEmpty() ||
                                first_name.isEmpty() || email.isEmpty() || parent_name.isEmpty() || parent_phone.isEmpty() ||
                                !email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
                                || (now.getYear() - Integer.parseInt(yyyy)) < 3) {
                            screenController.alert(Alert.AlertType.WARNING, "Can not creat Member", "Enter all fields");
                        } else {

                            if (memberService.create(first_name, las_name, parent_name, parent_phone,
                                    yyyy + '-' + mm + '-' + dd, birth_cert, med_date) > 0) {
                                char[] chars = "abcdefghijklmnopqrstuvwxyz1234ABDC".toCharArray();
                                StringBuilder sb = new StringBuilder(19);
                                Random random = new Random();
                                for (int i = 0; i < 19; i++) {
                                    char c = chars[random.nextInt(chars.length)];
                                    sb.append(c);
                                }
                                String password = sb.toString();
                                if (userService.create(email, password, "member", memberService.getLastAddedMember()) > 0) {
                                    screenController.alert(Alert.AlertType.INFORMATION, "OK", "New memeber created!\n Login " +
                                            email + " password" + password);
                                }
                            }
                            first_name_input.clear();
                            phone_input.clear();
                            email_input.clear();
                            birth_certificate_input.clear();
                            parent_name_input.clear();
                            phone_input.clear();
                            las_name_input.clear();
                        }
                    }
                }
            }
        });

    }
}

