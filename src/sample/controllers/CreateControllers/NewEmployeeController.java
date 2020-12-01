package sample.controllers.CreateControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.controllers.main.ScreenController;
import sample.service.StaffService;
import sample.service.UserService;

import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

import static sample.controllers.main.AuthorizationController.activeUser;

public class NewEmployeeController {
    @FXML
    private TextField last_name_input, specialization_input, first_name_input, email_input, phone_input, position_input;
    @FXML
    private Button add_employee_btn;
    @FXML
    private TextField work_hours_input;

    ScreenController screenController;
    StaffService staffService;
    UserService userService;

    public NewEmployeeController() {
        screenController = new ScreenController();
        staffService = new StaffService();
        try {
            userService = new UserService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
       work_hours_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) phone_input.setText(oldValue);
        });

        addTextLimiter(phone_input, 12);
        addTextLimiter(position_input, 20);
        addTextLimiter(last_name_input, 30);
        addTextLimiter(first_name_input, 30);
        addTextLimiter(specialization_input, 100);
        addTextLimiter(email_input, 20);


        add_employee_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String last_name, first_name, email, position, specialisation, phone, maxHours;
                last_name = last_name_input.getText().trim();
                first_name = first_name_input.getText().trim();
                email = email_input.getText().trim();
                phone = phone_input.getText().trim();
                position = position_input.getText().trim();
                specialisation = specialization_input.getText().trim();
                maxHours = work_hours_input.getText().trim();
                if (userService.isEmail(email)) {
                    ScreenController.alert(Alert.AlertType.ERROR,"Email error","This email is already in use");
                } else {
                    if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                        screenController.alert(Alert.AlertType.WARNING, "Wrong email", "Wrong email input!");
                    } else {
                        if (last_name.isEmpty() || first_name.isEmpty() || email.isEmpty() || position.isEmpty() || specialisation.isEmpty()
                                || phone.isEmpty() || maxHours.isEmpty()) {
                            screenController.alert(Alert.AlertType.WARNING, "Can not creat Employee", "Enter all fields");
                        } else {

                            if (staffService.create(first_name, last_name, position, phone, Integer.parseInt(maxHours), specialisation) > 0) {
                                char[] chars = "abcdefghijklmnopqrstuvwxyz1234ABDC".toCharArray();
                                StringBuilder sb = new StringBuilder(19);
                                Random random = new Random();
                                for (int i = 0; i < 19; i++) {
                                    char c = chars[random.nextInt(chars.length)];
                                    sb.append(c);
                                }
                                String password = sb.toString();
                                if (userService.create(email, password, "staff", staffService.getLastAddedEmployee()) > 0) {
                                    screenController.alert(Alert.AlertType.INFORMATION, "OK", "New Employee created!\n Login " +
                                            email + " password" + password);
                                    ScreenController.setNewAction(activeUser.getId(),"Новый сотрудник "+ first_name+" "+last_name +" добавлен");
                                }
                            }
                            first_name_input.clear();
                            phone_input.clear();
                            email_input.clear();
                            work_hours_input.clear();
                            specialization_input.clear();
                            phone_input.clear();
                            last_name_input.clear();
                            position_input.clear();
                        }
                    }
                }
            }
        });

    }

}
